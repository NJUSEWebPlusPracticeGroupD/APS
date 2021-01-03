package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.dao.BomRepository;
import com.webplusgd.aps.dao.OrderRepository;
import com.webplusgd.aps.dao.ResourceRepository;
import com.webplusgd.aps.domain.Bom;
import com.webplusgd.aps.domain.Order;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import com.webplusgd.aps.optaplanner.utils.DataUtil;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private static final Integer DEFAULT_STEP = 1;
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

    public ApsSolution generateStandardProblem(LocalDateTime currentTime) {
        // 取所有订单
        List<Order> orderListFromDb = orderRepository.findAll();
        // 组装成对应Order的关系
        List<com.webplusgd.aps.optaplanner.domain.Order> orderList = new ArrayList<>(orderListFromDb.size()*2);
        LocalDateTime latestEndTime = currentTime;
        for (Order orderFromDb : orderListFromDb) {
            List<Bom> order2BomFromDb = bomRepository.findByMaterialId(orderFromDb.getMaterialId());
            if (order2BomFromDb.size() == 0) {
                continue;
            }
            order2BomFromDb = order2BomFromDb.stream().filter(bom -> SELECTED_CRAFT.equals(bom.getCraft())).collect(Collectors.toList());
            //  取所需资源
            List<String> groupResourceIdList = new ArrayList<>(order2BomFromDb.stream()
                    .filter(bom -> bom.getResourceType() == 0 && bom.getResourceId().contains("-"))
                    .map(Bom::getResourceId)
                    .collect(Collectors.toSet()));
            List<String> machineResourceIdList = new ArrayList<>(order2BomFromDb.stream()
                    .filter(bom -> bom.getResourceType() == 1 && bom.getResourceId().startsWith("line"))
                    .map(Bom::getResourceId)
                    .collect(Collectors.toSet()));
            List<GroupResource> groupResourceList = resourceRepository.findAllById(groupResourceIdList).stream().map(resource -> new GroupResource(resource.getCount(), resource.getId(), getResourceShift(resource.getShiftCode()))).collect(Collectors.toList());
            List<MachineResource> machineResourceList = resourceRepository.findAllById(machineResourceIdList).stream().map(resource -> new MachineResource(resource.getCount(), resource.getId(), getResourceShift(resource.getShiftCode()))).collect(Collectors.toList());
            com.webplusgd.aps.optaplanner.domain.Order tmpOrder = new com.webplusgd.aps.optaplanner.domain.Order(orderFromDb.getOrderId(),
                    new Product(orderFromDb.getMaterialId(), groupResourceList, machineResourceList, order2BomFromDb.get(0).getCapacity(), order2BomFromDb.get(0).getQuota(), DEFAULT_STEP), orderFromDb.getOrderCount(),
                    DateUtil.date2LocalDateTime(orderFromDb.getDeliveryDate()),
                    null, null);
            if(tmpOrder.getProduct().isValid()) {
                orderList.add(tmpOrder);
                if (latestEndTime.isBefore(tmpOrder.getTermOfDeliver())) {
                    latestEndTime = tmpOrder.getTermOfDeliver();
                }
            }
        }
        latestEndTime = latestEndTime.plusDays(DAY_RANGE_LENGTH);
        Set<GroupResource> groupResourceSet=new HashSet<>();
        Set<MachineResource> machineResourceSet=new HashSet<>();
        for(com.webplusgd.aps.optaplanner.domain.Order order:orderList){
            if(order.getOrderId()!=0) {
                groupResourceSet.addAll(order.getProduct().getAvailableGroupResource());
                machineResourceSet.addAll(order.getProduct().getAvailableMachineResource());
            }
        }
        List<GroupResource> groupResourceList = new ArrayList<>(groupResourceSet);
        List<MachineResource> machineResourceList = new ArrayList<>(machineResourceSet);
        List<Resource> resourceList = new ArrayList<>();
        resourceList.addAll(groupResourceList);
        resourceList.addAll(machineResourceList);

        // 初始化timeslot
        List<Timeslot> timeslotList = getTimeslotData(currentTime, latestEndTime, 1);
        List<Task> taskList = getInitialTask(resourceList, timeslotList);
        return new ApsSolution(orderList, resourceList, timeslotList, DataUtil.groupOrderByOrderId(orderList), taskList);
    }

    /**
     * 获取时间片序列
     *
     * @param beginTime    起始时间
     * @param endTime      结束时间
     * @param timeslotSize 时间片大小（H）
     * @return
     */
    private List<Timeslot> getTimeslotData(LocalDateTime beginTime, LocalDateTime endTime, Integer timeslotSize) {
        List<Timeslot> result = new ArrayList<>();
        LocalDateTime time = beginTime;
        while (time.isBefore(endTime)) {
            Timeslot timeslot = new Timeslot();
            timeslot.setStartDateTime(time);
            time = time.plusHours(timeslotSize);
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

    private List<Task> getInitialTask(List<Resource> resourceList,
                                      List<Timeslot> timeslotList) {
        // 初始化排程单元(将计划单元按照时间和资源进行组织，大小为时间片数量*资源数量)
        List<Task> taskList = new ArrayList<>(resourceList.size() * timeslotList.size());
        com.webplusgd.aps.optaplanner.domain.Order defaultOrder = com.webplusgd.aps.optaplanner.domain.Order.getDefaultOrder();
        Task task;
        for (Timeslot timeslot : timeslotList) {
            for (com.webplusgd.aps.optaplanner.domain.resource.Resource resource : resourceList) {
                if (Shift.shiftTimeslotConflict(resource.getShift(), timeslot)) {
                    continue;
                }
                task = new Task(defaultOrder, resource, timeslot);
                taskList.add(task);
            }
        }
        return taskList;
    }

}
