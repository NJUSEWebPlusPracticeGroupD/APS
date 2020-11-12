package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Shift;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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

    private void scheduleInterval(ApsSolution problem) {
        List<Order> orderList = problem.getOrderList();

        orderList.sort((o1, o2) -> {
            if (o1.getTermOfDeliver().compareTo(o2.getTermOfDeliver()) != 0) {
                return o1.getTermOfDeliver().compareTo(o2.getTermOfDeliver());
            }
            if (o1.getOrderNum() > o2.getOrderNum()) {
                return 1;
            }
            if (o1.getOrderId() > o2.getOrderId()) {
                return 1;
            }
            return 0;
        });

        Map<Integer, List<Integer>> restAmount = new HashMap<>();
        int id;
        List<Integer> tempStepRest;
        for (Order order : orderList) {
            id = order.getOrderId();
            if (!restAmount.containsKey(id)) {
                restAmount.put(id, new ArrayList<>());
            }
            tempStepRest = restAmount.get(id);
            while (tempStepRest.size() < order.getProduct().getStep()) {
                tempStepRest.add(0);
            }
            tempStepRest.set(order.getProduct().getStep() - 1, (int) order.getOrderNum());
        }

        Set<Resource> occupied = new HashSet<>();
        int index = 0;
        List<ScheduledTask> result = new ArrayList<>(index * 2);
        int step;
        List<GroupResource> candidateHuman = new ArrayList<>();
        List<MachineResource> candidateLine = new ArrayList<>();
        List<Timeslot> timeslotList = problem.getTimeslotList();
        Map<Order, Integer> orderProduction = new HashMap<>();
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
                if (null != order.getProduct().getAvailableGroupResource()) {
                    for (GroupResource human : order.getProduct().getAvailableGroupResource()) {
                        if (occupied.contains(human) || Shift.shiftTimeslotConflict(human.getShift(), timeslot)) {
                            continue;
                        }
                        candidateHuman.add(human);
                    }
                }
                if (null != order.getProduct().getAvailableMachineResource()) {
                    for (MachineResource line : order.getProduct().getAvailableMachineResource()) {
                        if (occupied.contains(line) || Shift.shiftTimeslotConflict(line.getShift(), timeslot)) {
                            continue;
                        }
                        candidateLine.add(line);
                    }
                }
                int humanCapacity = 0;
                for (GroupResource humanResource : candidateHuman) {
                    humanCapacity += humanResource.getCapacity();
                }
                int lineNum = candidateLine.size();
                int production = 0;
                List<Integer> rest = restAmount.get(order.getOrderId());
                if (null == order.getProduct().getAvailableGroupResource()) {
                    production = lineNum * order.getProduct().getStandardCapacity();
                } else if (null == order.getProduct().getAvailableMachineResource()) {
                    production = humanCapacity / order.getProduct().getMinimumStaff() * order.getProduct().getStandardCapacity();
                } else {
                    production = Math.min(lineNum, humanCapacity / order.getProduct().getMinimumStaff()) * order.getProduct().getStandardCapacity();
                }
                if (step > 1) {
                    production = Math.min(production, rest.get(step - 1) - rest.get(step - 2));
                } else {
                    production = Math.min(rest.get(step - 1), production);
                }
                humanCapacity = (int) Math.ceil(production * 1.0 / order.getProduct().getStandardCapacity()) * order.getProduct().getMinimumStaff();
                lineNum = (int) Math.ceil(production * 1.0 / order.getProduct().getStandardCapacity());
                for (GroupResource humanResource : candidateHuman) {
                    if (humanCapacity <= 0) {
                        break;
                    }
                    humanCapacity -= humanResource.getCapacity();
                    result.add(new ScheduledTask(order, humanResource, timeslot, step));
                    occupied.add(humanResource);
                }
                for (MachineResource lineResource : candidateLine) {
                    if (lineNum <= 0) {
                        break;
                    }
                    lineNum -= 1;
                    result.add(new ScheduledTask(order, lineResource, timeslot, step));
                    occupied.add(lineResource);
                }
                orderProduction.put(order, production);
            }
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
    }

    @Override
    public List<ScheduledTask> getPlan(Timestamp currentTime) {
        ApsSolution problem = dataManager.generateStandardProblem(currentTime.toLocalDateTime());
        scheduleInterval(problem);
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
