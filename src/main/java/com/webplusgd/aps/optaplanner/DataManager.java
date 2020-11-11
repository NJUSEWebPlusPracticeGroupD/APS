package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.dao.BomRepository;
import com.webplusgd.aps.dao.OrderRepository;
import com.webplusgd.aps.dao.ResourceRepository;
import com.webplusgd.aps.domain.Bom;
import com.webplusgd.aps.domain.Order;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.optaplanner.domain.Product;
import com.webplusgd.aps.optaplanner.domain.Shift;
import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rollingegg
 * @create_time 11/9/2020 10:15 PM
 * 从数据库导出Solution所需的数据，包括Order, Resource, Bom, TimeslotList
 */
@Component
public class DataManager {
    private static final Integer DAY_RANGE_LENGTH = 7;
    private static final String SELECTED_CRAFT = "装配";
    /**
     * 以12小时为粒度划分子订单（子任务）
     */
    public static final Integer TASK_TIME_RANGE = 12;
    private OrderRepository orderRepository;

    private ResourceRepository resourceRepository;

    private BomRepository bomRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setBomRepository(BomRepository bomRepository) {
        this.bomRepository = bomRepository;
    }

    @Autowired
    public void setResourceRepository(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public ApsSolution generateProblem(LocalDateTime currentTime) {
        // 取所有订单
        List<Order> orderListFromDb = orderRepository.findAll();
        // 组装成对应Order的关系
        List<com.webplusgd.aps.optaplanner.domain.Order> orderList = new ArrayList<>();
        LocalDateTime latestEndTime = currentTime;
        for (Order orderFromDb : orderListFromDb) {
            List<Bom> order2BomFromDb = bomRepository.findByMaterialId(orderFromDb.getMaterialId());
            if (order2BomFromDb.size() == 0) {
                continue;
            }
            order2BomFromDb = order2BomFromDb.stream().filter(bom -> SELECTED_CRAFT.equals(bom.getCraft())).collect(Collectors.toList());
            //  取所需资源
            List<String> groupResourceIdList = order2BomFromDb.stream().filter(bom -> bom.getResourceType() == 0).map(Bom::getResourceId).collect(Collectors.toList());
            List<String> machineResourceIdList = order2BomFromDb.stream().filter(bom -> bom.getResourceType() == 1).map(Bom::getResourceId).collect(Collectors.toList());
            List<GroupResource> groupResourceList = resourceRepository.findAllById(groupResourceIdList).stream().map(resource -> new GroupResource(resource.getCount(), resource.getId(), getResourceShift(resource.getShiftCode()))).collect(Collectors.toList());
            List<MachineResource> machineResourceList = resourceRepository.findAllById(machineResourceIdList).stream().map(resource -> new MachineResource(resource.getCount(), resource.getId(), getResourceShift(resource.getShiftCode()))).collect(Collectors.toList());
            // 拆分机器资源,重新命名为 linexx-i
            List<MachineResource> splitResourceList = new ArrayList<>();
            for (MachineResource machineResource : machineResourceList) {
                for (int i = 0; i < machineResource.getCapacity(); i++) {
                    splitResourceList.add(new MachineResource(1, machineResource.getName() + "-" + i, machineResource.getShift()));
                }
            }
            com.webplusgd.aps.optaplanner.domain.Order tmpOrder = new com.webplusgd.aps.optaplanner.domain.Order(orderFromDb.getOrderId(),
                    new Product(orderFromDb.getMaterialId(), groupResourceList, splitResourceList, order2BomFromDb.get(0).getCapacity(), order2BomFromDb.get(0).getQuota()), orderFromDb.getOrderCount(),
                    DateUtil.date2LocalDateTime(orderFromDb.getDeliveryDate()),
                    null, null);
            orderList.add(tmpOrder);
            if (latestEndTime.isBefore(tmpOrder.getTermOfDeliver())) {
                latestEndTime = tmpOrder.getTermOfDeliver();
            }
        }
        latestEndTime.plusDays(DAY_RANGE_LENGTH);
        // 取订单对应的工艺路线
        List<Integer> materialIdList = orderListFromDb.stream().map(Order::getMaterialId).collect(Collectors.toList());
        List<Bom> bomListFromDb = bomRepository.findByMaterialIdIn(materialIdList);
        //  取所需资源
        List<String> groupResourceIdList = bomListFromDb.stream().filter(bom -> bom.getResourceType() == 0).map(Bom::getResourceId).collect(Collectors.toList());
        List<String> machineResourceIdList = bomListFromDb.stream().filter(bom -> bom.getResourceType() == 1 && bom.getResourceId().startsWith("line")).map(Bom::getResourceId).collect(Collectors.toList());
        List<GroupResource> groupResourceList = resourceRepository.findAllById(groupResourceIdList).stream().map(resource -> new GroupResource(resource.getCount(), resource.getId(), getResourceShift(resource.getShiftCode()))).collect(Collectors.toList());
        List<MachineResource> machineResourceList = resourceRepository.findAllById(machineResourceIdList).stream().map(resource -> new MachineResource(resource.getCount(), resource.getId(), getResourceShift(resource.getShiftCode()))).collect(Collectors.toList());

        // 拆分机器资源,重新命名为 linexx-i
        List<MachineResource> splitResourceList = new ArrayList<>();
        for (MachineResource machineResource : machineResourceList) {
            for (int i = 0; i < machineResource.getCapacity(); i++) {
                splitResourceList.add(new MachineResource(1, machineResource.getName() + "-" + i, machineResource.getShift()));
            }
        }

        // 初始化timeslot
        List<Timeslot> timeslotList = getTimeslotData(currentTime, latestEndTime);
        // 初始化Task分配实体，需要提前分配好order，这里先计算每个order按照标准生产速度最多需要多少个分配单元(!!!默认一倍速!!!)
        int taskListSize = timeslotList.size() * groupResourceList.size();
        List<Task> taskList = new ArrayList<>(taskListSize);
        for (com.webplusgd.aps.optaplanner.domain.Order toAllocatedOrder : orderList) {
            int subOrderIndex = 0;
            Product productBom = toAllocatedOrder.getProduct();
//            int availableHumanNum = productBom.getAvailableGroupResource().stream().mapToInt(GroupResource::getCapacity).sum();
            int remainHours = (int) Math.ceil(1.0 * toAllocatedOrder.getOrderNum() / productBom.getStandardCapacity());
            while (remainHours > TASK_TIME_RANGE) {
                taskList.add(new Task(toAllocatedOrder, toAllocatedOrder.getOrderId() + "_" + (++subOrderIndex), TASK_TIME_RANGE));
                remainHours -= TASK_TIME_RANGE;
            }
            // 按照一倍速生产订单，最后可能切到余留n小时，那么这个子任务只需工作n小时就能完成订单了
            taskList.add(new Task(toAllocatedOrder, toAllocatedOrder.getOrderId() + "_" + (++subOrderIndex), remainHours));
//            int allocatedNum = (int) Math.ceil(1.0 * toAllocatedOrder.getOrderNum() / productBom.getStandardCapacity() / TASK_TIME_RANGE);
//            for (int i = 0; i < allocatedNum; i++) {
//                taskList.add(new Task(toAllocatedOrder, toAllocatedOrder.getOrderId() + "_" + i));
//            }
        }
        return new ApsSolution(orderList, groupResourceList, splitResourceList, timeslotList, taskList);
    }

    private List<Timeslot> getTimeslotData(LocalDateTime beginTime, LocalDateTime endTime) {
        List<Timeslot> result = new ArrayList<>();
        LocalDateTime time = beginTime;
        while (time.isBefore(endTime)) {
            Timeslot timeslot = new Timeslot();
            timeslot.setStartDateTime(time);
            time = time.plusHours(TASK_TIME_RANGE);
            timeslot.setEndDateTime(time);
            result.add(timeslot);
        }
        return result;
    }

    private Shift getResourceShift(int type) {
        switch (type) {
            case 1:
                return Shift.DAY_SHIFT;
            case 2:
                return Shift.NIGHT_SHIFT;
            default:
                return Shift.ALL_TIME_SHIFT;
        }
    }
}
