package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ResourceGanttChartService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            List<ScheduledTask> scheduledTasks = null;
            ArrayList<ResourceGanttItem> resourceGanttItems = new ArrayList<>();

            // 指定日期的早晨7点
            LocalDateTime startDateTime = DateUtil.date2LocalDateTime(date).plusHours(7);
            // 第二天早晨7点
            LocalDateTime endDateTime = startDateTime.plusDays(1);

            for (ScheduledTask scheduledTask : scheduledTasks) {
                if (startDateTime.isBefore(scheduledTask.getTimeslot().getStartDateTime()) && scheduledTask.getTimeslot().getEndDateTime().isBefore(endDateTime)) {
                    long duration = scheduledTask.getTimeslot().getDurationOfHours();
                    boolean delay = ((duration * scheduledTask.getOrder().getProduct().getStandardCapacity()) >= scheduledTask.getOrder().getOrderNum());
                    ResourceGanttItem resourceGanttItem = new ResourceGanttItem(scheduledTask.getResource().getName(), DateUtil.localDateTime2Date(scheduledTask.getOrder().getStartTime()), DateUtil.localDateTime2Date(scheduledTask.getOrder().getFinishTime()), Integer.toString(scheduledTask.getOrder().getOrderId()), delay);
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
