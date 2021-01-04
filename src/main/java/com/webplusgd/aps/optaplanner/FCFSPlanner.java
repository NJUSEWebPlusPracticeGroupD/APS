package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.*;
import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import com.webplusgd.aps.optaplanner.optional.OrderStrengthComparator;
import com.webplusgd.aps.optaplanner.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rollingegg
 * @create_time 11/11/2020 5:43 PM
 */
@Slf4j
@Component
public class FCFSPlanner implements Planner {

    @Autowired
    private DataManager dataManager;

    private LocalDateTime scheduleStartTime;
    private List<ScheduledTask> planList;

    public List<Task> scheduleInterval(ApsSolution problem) {
        List<Order> orderList = new ArrayList<>(problem.getOrderList());
        orderList.remove(Order.getDefaultOrder());

        orderList.sort(new OrderStrengthComparator());

        // 创建存储订单剩余量的数据结构
        Map<Integer, List<Integer>> restAmount = DataUtil.groupOrderAmountByOrderIdAndStep(orderList);

        // 将task列表整理为map，方便后续迭代过程中快速查找排程单元
        Map<Timeslot, Map<Resource, Task>> taskMap=DataUtil.groupTaskByTimeslotAndResource(problem.getTaskList());

        // 声明迭代过程中的临时变量
        Set<Resource> occupied = new HashSet<>();
        List<ScheduledTask> result = new ArrayList<>();
        int step;
        List<GroupResource> candidateHuman = new ArrayList<>();
        List<MachineResource> candidateLine = new ArrayList<>();
        List<Timeslot> timeslotList = problem.getTimeslotList();
        // 每个工时的订单产量
        Map<Order, Integer> orderProduction = new HashMap<>();
        Task task;
        for (Timeslot timeslot : timeslotList) {
            if (orderList.size() == 0) {
                break;
            }
            occupied.clear();
            orderProduction.clear();
            for (Order order : orderList) {
                step = order.getProduct().getStep();
                candidateHuman.clear();
                candidateLine.clear();
                // 获取生产订单的备选人员
                if (null != order.getProduct().getAvailableGroupResource()) {
                    for (GroupResource human : order.getProduct().getAvailableGroupResource()) {
                        if (occupied.contains(human) || Shift.shiftTimeslotConflict(human.getShift(), timeslot)) {
                            continue;
                        }
                        candidateHuman.add(human);
                    }
                }
                // 获取生产订单的备选设备
                if (null != order.getProduct().getAvailableMachineResource()) {
                    for (MachineResource line : order.getProduct().getAvailableMachineResource()) {
                        if (occupied.contains(line) || Shift.shiftTimeslotConflict(line.getShift(), timeslot)) {
                            continue;
                        }
                        candidateLine.add(line);
                    }
                }
                // 计算可能的最大产能
                int humanCapacity = 0;
                for (GroupResource humanResource : candidateHuman) {
                    humanCapacity += humanResource.getCapacity();
                }
                int lineNum = candidateLine.size();
                int production = Product.calculateProduction(humanCapacity,lineNum,order.getProduct());
                List<Integer> rest = restAmount.get(order.getOrderId());
                if (step > 1) {
                    production = Math.min(production, rest.get(step - 1) - rest.get(step - 2));
                } else {
                    production = Math.min(rest.get(step - 1), production);
                }
                // 基于产能产量进行资源排程安排
                Product.optimizeArrange(candidateHuman,candidateLine, order.getProduct(), production);
                for (GroupResource humanResource : candidateHuman) {
                    result.add(new ScheduledTask(order, humanResource, timeslot, step));
                    // 构造排程任务
                    task=taskMap.get(timeslot).get(humanResource);
                    task.setOrder(order);
                    occupied.add(humanResource);
                }
                for (MachineResource lineResource : candidateLine) {
                    result.add(new ScheduledTask(order, lineResource, timeslot, step));
                    // 构造排程任务
                    task=taskMap.get(timeslot).get(lineResource);
                    task.setOrder(order);
                    occupied.add(lineResource);
                }
                orderProduction.put(order, production);
            }
            // 基于当前工时的排程计划的产量进行订单的剩余量更新
            for (Map.Entry<Order, Integer> entry : orderProduction.entrySet()) {
                Order order = entry.getKey();
                int production = entry.getValue();
                List<Integer> rest = restAmount.get(order.getOrderId());
                step = order.getProduct().getStep();
                rest.set(step - 1, rest.get(step - 1) - production);
                if (rest.get(step - 1) <= 0) {
                    orderList.remove(order);
                    order.setFinishTime(timeslot.getEndDateTime());
                }
            }
        }
        planList = result;
        return DataUtil.toResult(problem.getTaskList());
    }

    @Override
    public List<ScheduledTask> getPlan(Timestamp currentTime) {
        ApsSolution problem = dataManager.generateStandardProblem(currentTime.toLocalDateTime());
        List<ScheduledTask> res=scheduleInterval(problem).stream().map(ScheduledTask::new).collect(Collectors.toList());
        return waitForPlan();
    }


    @Override
    public void startSchedule(LocalDateTime currentTime) {
        scheduleStartTime = currentTime;
        log.info("当前系统时间（排程启动时间）设置为：{}",currentTime);
        ApsSolution problem = dataManager.generateStandardProblem(scheduleStartTime);
        scheduleInterval(problem);
    }

    @Override
    public List<ScheduledTask> tryGetPlan() {
        return planList;
    }

    @Override
    public List<ScheduledTask> waitForPlan() {
        return planList;
    }
}
