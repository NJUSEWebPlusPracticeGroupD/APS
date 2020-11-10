package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.OrderGanttChartService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResponseVO;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderGattChartService implements OrderGanttChartService {

    private final OptaPlanner optaPlanner;

    public OrderGattChartService(OptaPlanner optaPlanner) {
        this.optaPlanner = optaPlanner;
    }

    @Data
    static class TraitsOfHour {
        Date date;
        int resourceNum = 0;
        int standardCapacity;
        int minimumStaff;

        boolean equals(LocalDateTime dateTime) {
            return date.equals(DateUtil.localDateTime2Date(dateTime));
        }
    }

    @Override
    public ResponseVO<ArrayList<OrderGanttItem>> getOrderGanttChart(Date date) {
        try {
            List<ScheduledTask> scheduledTasks = null;
            List<Integer> orderIds = new ArrayList<>();
            ArrayList<OrderGanttItem> orderGanttItems = new ArrayList<>();

            // 扫描排程计划表，记录所有订单 id
            for (ScheduledTask scheduledTask : scheduledTasks) {
                if (!orderIds.contains(scheduledTask.getOrder().getOrderId())) {
                    orderIds.add(scheduledTask.getOrder().getOrderId());
                }
            }

            LocalDateTime dueDate = DateUtil.date2LocalDateTime(date).plusDays(1);
            for (Integer orderId : orderIds) {
                long achieved = 0, goal = 1;

                List<TraitsOfHour> traitsOfHours = new ArrayList<>();
                for (ScheduledTask scheduledTask : scheduledTasks) {
                    if (orderId == scheduledTask.getOrder().getOrderId()) {
                        if (scheduledTask.getOrder().getFinishTime().isBefore(dueDate)) {
                            int index = -1;
                            for (int i = 0; i < traitsOfHours.size(); i++) {
                                if (traitsOfHours.get(i).equals(scheduledTask.getOrder().getStartTime())) {
                                    index = i;
                                    break;
                                }
                            }
                            if (index == -1) {
                                TraitsOfHour traitsOfHour = new TraitsOfHour();
                                traitsOfHour.setDate(DateUtil.localDateTime2Date(scheduledTask.getOrder().getStartTime()));
                                traitsOfHour.setResourceNum(scheduledTask.getResource().getCapacity());
                                traitsOfHour.setStandardCapacity(scheduledTask.getOrder().getProduct().getStandardCapacity());
                                traitsOfHour.setMinimumStaff(scheduledTask.getOrder().getProduct().getMinimumStaff());
                                traitsOfHours.add(traitsOfHour);
                            } else {
                                traitsOfHours.get(index).setResourceNum(traitsOfHours.get(index).getResourceNum() + scheduledTask.getResource().getCapacity());
                            }
                        }

                        goal = scheduledTask.getOrder().getOrderNum();
                    }
                }
                for (TraitsOfHour traitsOfHour : traitsOfHours) {
                    achieved += traitsOfHour.getResourceNum() * traitsOfHour.getStandardCapacity() / traitsOfHour.getMinimumStaff();
                }

                int progress = (int) (achieved / goal);
                OrderGanttItem orderGanttItem = new OrderGanttItem(Integer.toString(orderId), progress, 100 - progress);
                orderGanttItems.add(orderGanttItem);
            }

            return ResponseVO.buildSuccess(orderGanttItems);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取订单甘特图失败！");
        }
    }

}
