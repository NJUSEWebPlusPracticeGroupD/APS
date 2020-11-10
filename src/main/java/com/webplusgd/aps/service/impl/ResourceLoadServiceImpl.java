package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.Planner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ResourceLoadService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.ResourceLoadChart;
import optaplanner.domain.Shift;
import optaplanner.domain.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rollingegg
 * @create_time 11/9/2020 11:13 PM
 */
@Service("ResourceLoadService")
public class ResourceLoadServiceImpl implements ResourceLoadService {
    @Autowired
    private OptaPlanner planner;

    @Override
    public ResourceLoadChart getResourceLoadChart(Date startDate) {
        // TODO 返回资源负载图
        List<ScheduledTask> output = planner.getPlan(new Timestamp(System.currentTimeMillis()));
        // 从指定日期的7点开始计算
        LocalDateTime startDateTime = DateUtil.date2LocalDateTime(startDate).plusHours(7);
        LocalDateTime endDateTime = startDateTime.plusDays(7);
        // 取出指定日期一周内的生产任务
        List<ScheduledTask> amongResult = output.stream().filter(o -> o.getTimeslot().getStartDateTime().isAfter(startDateTime)
                && endDateTime.isAfter(o.getTimeslot().getEndDateTime()))
                .collect(Collectors.toList());
        HashMap<Resource, List<Integer>> resourceWorkTime = new HashMap<>();
        for (ScheduledTask task : amongResult) {
            resourceWorkTime.putIfAbsent(task.getResource(), Arrays.asList(0, 0, 0, 0, 0, 0, 0));
            List<Integer> workTimeList = resourceWorkTime.get(task.getResource());
            if (Shift.DAY_SHIFT.equals(task.getResource().getShift())) {
                int dayIndex = (int) DateUtil.daysDiff(task.getTimeslot().getStartDateTime(), startDateTime);
                workTimeList.set(dayIndex, (int) (workTimeList.get(dayIndex)+task.getTimeslot().getDurationOfHours()));
            }
        }
        return new ResourceLoadChart();
    }
}
