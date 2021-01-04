package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.FCFSPlanner;
import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ResourceGanttChartService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service("ResourceGanttChartService")
public class ResourceGattChartServiceImpl implements ResourceGanttChartService {

    private final OptaPlanner optaPlanner;

    public ResourceGattChartServiceImpl(OptaPlanner optaPlanner) {
        this.optaPlanner = optaPlanner;
    }

    @Override
    public ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(Date date) {
        try {
            List<ScheduledTask> scheduledTasks = optaPlanner.tryGetPlan();

            if (scheduledTasks == null) {
                return ResponseVO.buildFailure("还未进行排程！");
            }

            ArrayList<ResourceGanttItem> resourceGanttItems = new ArrayList<>();
            ArrayList<String> resourceNames = new ArrayList<>();

            // 指定日期的早晨7点
            LocalDateTime startDateTime = DateUtil.date2LocalDateTime(date).plusHours(7);
            // 第二天早晨7点
            LocalDateTime endDateTime = startDateTime.plusDays(1);

            // 第一步：默认所有在 date 早晨7点到第二天早晨7点之间的排程未延期，添加进结果列表
            for (ScheduledTask scheduledTask : scheduledTasks) {
                if (startDateTime.isBefore(scheduledTask.getTimeslot().getStartDateTime()) && scheduledTask.getTimeslot().getEndDateTime().isBefore(endDateTime)) {
                    ResourceGanttItem resourceGanttItem = new ResourceGanttItem(scheduledTask.getResource().getName(), DateUtil.localDateTime2Date(scheduledTask.getTimeslot().getStartDateTime()), DateUtil.localDateTime2Date(scheduledTask.getTimeslot().getEndDateTime()), Integer.toString(scheduledTask.getOrder().getOrderId()), false);
                    resourceGanttItems.add(resourceGanttItem);
                    if (!resourceNames.contains(scheduledTask.getResource().getName())) {
                        resourceNames.add(scheduledTask.getResource().getName());
                    }
                }
            }
            // 第二步：对于当前结果列表中的所有排程，判断其是否延期
            for (ResourceGanttItem resourceGanttItem : resourceGanttItems) {
                LocalDateTime wholeFinishTime = startDateTime;
                LocalDateTime termOfDeliver = startDateTime;
                for (ScheduledTask scheduledTask : scheduledTasks) {
                    if (resourceGanttItem.getName().equals(scheduledTask.getResource().getName()) && resourceGanttItem.getTask().equals(Integer.toString(scheduledTask.getOrder().getOrderId()))) {
                        if (wholeFinishTime.isBefore(scheduledTask.getOrder().getFinishTime())) {
                            wholeFinishTime = scheduledTask.getOrder().getFinishTime();
                        }
                        termOfDeliver = scheduledTask.getOrder().getTermOfDeliver();
                    }
                }
                resourceGanttItem.setDelay(termOfDeliver.isBefore(wholeFinishTime));
            }

            ArrayList<ResourceGanttItem> result = new ArrayList<>();
            for (String resourceName : resourceNames) {
                ResourceGanttItem resourceGanttItem = new ResourceGanttItem();
                resourceGanttItem.setName(resourceName);
                Date fromDate = DateUtil.localDateTime2Date(endDateTime), toDate = DateUtil.localDateTime2Date(startDateTime);
                boolean delay = false;
                String task = "";
                for (ResourceGanttItem tmp : resourceGanttItems) {
                    if (tmp.getName().equals(resourceName)) {
                        delay = tmp.isDelay();
                        task = tmp.getTask();
                        if (tmp.getFromDate().before(fromDate)) {
                            fromDate = tmp.getFromDate();
                        }
                        if (tmp.getToDate().after(toDate)) {
                            toDate = tmp.getToDate();
                        }
                    }
                }
                resourceGanttItem.setFromDate(fromDate);
                resourceGanttItem.setToDate(toDate);
                resourceGanttItem.setDelay(delay);
                resourceGanttItem.setTask(task);
                result.add(resourceGanttItem);
            }

            convert(result);
            return ResponseVO.buildSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取资源甘特图失败！");
        }
    }

    private void convert(ArrayList<ResourceGanttItem> resourceGanttItems) {
        Collections.sort(resourceGanttItems);
    }

}
