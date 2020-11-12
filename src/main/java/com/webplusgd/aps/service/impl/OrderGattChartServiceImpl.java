package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.FCFSPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.OrderGanttChartService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.OrderGanttChart;
import com.webplusgd.aps.vo.ResponseVO;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("OrderGanttChartService")
public class OrderGattChartServiceImpl implements OrderGanttChartService {

    private final FCFSPlanner fcfsPlanner;

    public OrderGattChartServiceImpl(FCFSPlanner fcfsPlanner) {
        this.fcfsPlanner = fcfsPlanner;
    }

    @Data
    static class TraitsOfHour {
        Date date;
        int resourceNum = 0;
        int standardCapacity;

        boolean equals(LocalDateTime dateTime) {
            return date.equals(DateUtil.localDateTime2Date(dateTime));
        }
    }

    private void getTraitsOfHours(List<TraitsOfHour> traitsOfHours, ScheduledTask scheduledTask) {
        int index = -1;
        for (int i = 0; i < traitsOfHours.size(); i++) {
            if (traitsOfHours.get(i).equals(scheduledTask.getTimeslot().getStartDateTime())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            TraitsOfHour traitsOfHour = new TraitsOfHour();
            traitsOfHour.setDate(DateUtil.localDateTime2Date(scheduledTask.getOrder().getFinishTime().minusDays(1)));
            traitsOfHour.setResourceNum(1);
            traitsOfHour.setStandardCapacity(scheduledTask.getOrder().getProduct().getStandardCapacity());
            traitsOfHours.add(traitsOfHour);
        } else {
            traitsOfHours.get(index).setResourceNum(traitsOfHours.get(index).getResourceNum() + 1);
        }
    }

    @Override
    public ResponseVO<OrderGanttChart> getOrderGanttChart(Date date) {
        try {
            List<ScheduledTask> scheduledTasks = fcfsPlanner.tryGetPlan();

            if (scheduledTasks == null) {
                return ResponseVO.buildFailure("获取订单甘特图失败！");
            }

            List<Integer> orderIds = new ArrayList<>();
            ArrayList<OrderGanttItem> orderGanttItems = new ArrayList<>();
            int orderCount = 0, onTimeCount = 0;
            LocalDateTime earliestDate = scheduledTasks.get(0).getOrder().getFinishTime();

            // 扫描排程计划表，记录所有订单 id
            for (ScheduledTask scheduledTask : scheduledTasks) {
                if (!orderIds.contains(scheduledTask.getOrder().getOrderId())) {
                    orderIds.add(scheduledTask.getOrder().getOrderId());
                }
                if (scheduledTask.getTimeslot().getStartDateTime().isBefore(earliestDate)) {
                    earliestDate = scheduledTask.getTimeslot().getStartDateTime();
                }
                orderCount++;
            }

            LocalDateTime dueDate = DateUtil.date2LocalDateTime(date).plusDays(1);
            for (Integer orderId : orderIds) {
                long achieved = 0, goal = 0, todo = 0;

                List<TraitsOfHour> achievedTraitsOfHours = new ArrayList<>();
                List<TraitsOfHour> todoTraitsOfHours = new ArrayList<>();
                for (ScheduledTask scheduledTask : scheduledTasks) {
                    if (orderId == scheduledTask.getOrder().getOrderId()) {
                        if (scheduledTask.getOrder().getFinishTime().isBefore(dueDate)) {
                            getTraitsOfHours(achievedTraitsOfHours, scheduledTask);
                            onTimeCount++;
                        } else if (dueDate.isBefore(scheduledTask.getTimeslot().getStartDateTime()) && scheduledTask.getOrder().getFinishTime().isBefore(scheduledTask.getOrder().getTermOfDeliver())) {
                            getTraitsOfHours(todoTraitsOfHours, scheduledTask);
                        }

                        goal += scheduledTask.getOrder().getProduct().getStandardCapacity();
                    }
                }

                for (TraitsOfHour achievedTraitsOfHour : achievedTraitsOfHours) {
                    achieved += achievedTraitsOfHour.getResourceNum() * achievedTraitsOfHour.getStandardCapacity();
                }
                for (TraitsOfHour todoTraitsOfHour : todoTraitsOfHours) {
                    todo += todoTraitsOfHour.getResourceNum() * todoTraitsOfHour.getStandardCapacity();
                }

                int progress = (int) (achieved * 100 / goal);
                int progressDelay = 100 - progress - (int) (todo * 100 / goal);
                OrderGanttItem orderGanttItem = new OrderGanttItem(Integer.toString(orderId), progress, progressDelay);
                orderGanttItems.add(orderGanttItem);
            }

            double onTimeDelivery;
            if (DateUtil.date2LocalDateTime(date).isBefore(earliestDate)) {
                onTimeDelivery = 100;
            } else {
                onTimeDelivery = onTimeCount * 100.0 / orderCount;
            }
            OrderGanttChart orderGanttChart = new OrderGanttChart(onTimeDelivery, orderGanttItems);
            return ResponseVO.buildSuccess(orderGanttChart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取订单甘特图失败！");
        }
    }

}
