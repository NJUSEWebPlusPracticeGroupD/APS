package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.OrderGanttChartService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResponseVO;
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

            LocalDateTime currentDate = DateUtil.date2LocalDateTime(date);
            // 对于每个 order
            for (Integer orderId : orderIds) {
                int achieved = 0, goal = 1;
                boolean firstScan = true;
                // 遍历一遍排程计划表，累加得到截止指定日期的生产量
                for (ScheduledTask scheduledTask : scheduledTasks) {
                    if (orderId == scheduledTask.getOrder().getOrderId()) {
                        if (scheduledTask.getOrder().getFinishTime().isBefore(currentDate)) {
                            // 若排程的结束日期在指定日期之前，说明已完成，直接与排程的总生产量相加
                            achieved += scheduledTask.getTimeslot().getDurationOfHours() * scheduledTask.getOrder().getProduct().getStandardCapacity();
                        } else if (scheduledTask.getOrder().getStartTime().isBefore(currentDate)) {
                            // 否则，若排程的开始日期在指定日期之前，说明正在进行但未完成，与该排程中截止指定日期的生产量相加
                            achieved += DateUtil.hoursDiff(scheduledTask.getOrder().getStartTime(), currentDate) * scheduledTask.getOrder().getProduct().getStandardCapacity();
                        }

                        if (firstScan) {
                            goal = scheduledTask.getOrder().getOrderNum();
                            firstScan = false;
                        }
                    }
                }
                int progress = achieved / goal;
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
