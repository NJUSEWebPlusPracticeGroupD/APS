package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ResourceGanttChartService;
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
public class ResourceGattChartService implements ResourceGanttChartService {

    private final OptaPlanner optaPlanner;

    public ResourceGattChartService(OptaPlanner optaPlanner) {
        this.optaPlanner = optaPlanner;
    }

    @Override
    public ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(Date date) {
        try {
            List<ScheduledTask> scheduledTasks = optaPlanner.getPlan(new Timestamp(date.getTime()));
            ArrayList<ResourceGanttItem> resourceGanttItems = new ArrayList<>();

            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime today = date.toInstant().atZone(zoneId).toLocalDateTime();
            LocalDateTime startDateTime = LocalDateTime.of(today.toLocalDate(), LocalTime.MIN.withHour(7));
            LocalDateTime endDateTime = startDateTime.plusDays(1);

            for (ScheduledTask scheduledTask : scheduledTasks) {
                if (startDateTime.isBefore(scheduledTask.getTimeslot().getStartDateTime()) && scheduledTask.getTimeslot().getEndDateTime().isBefore(endDateTime)) {
                    Date fromDate = Date.from(scheduledTask.getTimeslot().getStartDateTime().atZone(zoneId).toInstant());
                    Date toDate = Date.from(scheduledTask.getTimeslot().getEndDateTime().atZone(zoneId).toInstant());
                    int discrepantDays = (int) ((date.getTime() - fromDate.getTime()) / 1000 / 60 / 60 / 24);
                    boolean delay = ((discrepantDays * scheduledTask.getOrder().getProduct().getStandardCapacity()) >= scheduledTask.getOrder().getOrderNum());
                    ResourceGanttItem resourceGanttItem = new ResourceGanttItem(scheduledTask.getResource().getName(), fromDate, toDate, Integer.toString(scheduledTask.getOrder().getOrderId()), delay);
                    resourceGanttItems.add(resourceGanttItem);
                }
            }

            return ResponseVO.buildSuccess(resourceGanttItems);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取资源甘特图失败！");
        }
    }

}
