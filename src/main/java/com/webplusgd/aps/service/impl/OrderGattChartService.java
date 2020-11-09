package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.Planner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.OrderGanttChartService;
import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
            List<ScheduledTask> scheduledTasks = optaPlanner.getPlan(new Timestamp(date.getTime()));
            ArrayList<OrderGanttItem> orderGanttItems = new ArrayList<>();

            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime today = date.toInstant().atZone(zoneId).toLocalDateTime();
            LocalDateTime startDateTime = LocalDateTime.of(today.toLocalDate(), LocalTime.MIN.withHour(0));
            LocalDateTime endDateTime = startDateTime.plusDays(1);

            for (ScheduledTask scheduledTask : scheduledTasks) {
                if (startDateTime.isBefore(scheduledTask.getTimeslot().getStartDateTime()) && scheduledTask.getTimeslot().getEndDateTime().isBefore(endDateTime)) {
                    Date fromDate = Date.from(scheduledTask.getTimeslot().getStartDateTime().atZone(zoneId).toInstant());
                    int discrepantDays = (int) ((date.getTime() - fromDate.getTime()) / 1000 / 60 / 60 / 24);
                    int progress = (discrepantDays * scheduledTask.getOrder().getProduct().getStandardCapacity()) / scheduledTask.getOrder().getOrderNum();
                    OrderGanttItem orderGanttItem = new OrderGanttItem(Integer.toString(scheduledTask.getOrder().getOrderId()), progress, 100 - progress);
                    orderGanttItems.add(orderGanttItem);
                }
            }

            return ResponseVO.buildSuccess(orderGanttItems);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取订单甘特图失败！");
        }
    }
}
