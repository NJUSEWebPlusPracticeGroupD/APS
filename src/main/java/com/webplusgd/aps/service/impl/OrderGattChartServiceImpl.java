package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.OrderGanttChartService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.OrderGanttChart;
import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("OrderGanttChartService")
public class OrderGattChartServiceImpl implements OrderGanttChartService {

    private final OptaPlanner optaPlanner;

    public OrderGattChartServiceImpl(OptaPlanner optaPlanner) {
        this.optaPlanner = optaPlanner;
    }

    @Override
    public ResponseVO<OrderGanttChart> getOrderGanttChart(Date date) {
        try {
            List<ScheduledTask> scheduledTasks = optaPlanner.tryGetPlan();

            if (scheduledTasks == null || scheduledTasks.size() == 0) {
                return ResponseVO.buildFailure("还未进行排程！");
            }

            ArrayList<OrderGanttItem> orderGanttItems = new ArrayList<>();
            scheduledTasks.removeIf(scheduledTask -> !scheduledTask.getResource().getName().startsWith("line"));
            LocalDateTime nextDate = DateUtil.date2LocalDateTime(date).plusDays(1);

            List<Integer> orderIds = new ArrayList<>();
            for (ScheduledTask scheduledTask : scheduledTasks) {
                if (!orderIds.contains(scheduledTask.getOrder().getOrderId())) {
                    orderIds.add(scheduledTask.getOrder().getOrderId());
                }
            }

            for (Integer orderId : orderIds) {
                OrderGanttItem orderGanttItem = new OrderGanttItem();
                orderGanttItem.setOrderId(Integer.toString(orderId));
                long archived = 0, delayed = 0, goal = 0;
                for (ScheduledTask scheduledTask : scheduledTasks) {
                    if (orderId == scheduledTask.getOrder().getOrderId()) {
                        if (scheduledTask.getOrder().getFinishTime().isAfter(scheduledTask.getOrder().getTermOfDeliver())) {
                            delayed += scheduledTask.getOrder().getProduct().getStandardCapacity() * scheduledTask.getTimeslot().getDurationOfHours();
                        } else if (scheduledTask.getTimeslot().getEndDateTime().isBefore(nextDate)) {
                            archived += scheduledTask.getOrder().getProduct().getStandardCapacity() * scheduledTask.getTimeslot().getDurationOfHours();
                        }
                        goal += scheduledTask.getOrder().getProduct().getStandardCapacity();
                    }
                }

                int progress = (int) (archived * 100 / goal);
                int progressDelay = (int) (delayed * 100 / goal);
                orderGanttItem.setProgress(progress);
                orderGanttItem.setProgressDelay(progressDelay);
                orderGanttItems.add(orderGanttItem);
            }

            long delayedOrderNum = 0;
            for (Integer orderId : orderIds) {
                for (ScheduledTask scheduledTask : scheduledTasks) {
                    if (orderId == scheduledTask.getOrder().getOrderId()) {
                        if (!scheduledTask.getOrder().getFinishTime().isAfter(scheduledTask.getOrder().getTermOfDeliver())) {
                            delayedOrderNum++;
                        }
                        break;
                    }
                }
            }

            double onTimeDelivery = delayedOrderNum * 100.0 / orderIds.size();
            OrderGanttChart orderGanttChart = new OrderGanttChart(onTimeDelivery, orderGanttItems);

            return ResponseVO.buildSuccess(orderGanttChart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取订单甘特图失败！");
        }
    }

}
