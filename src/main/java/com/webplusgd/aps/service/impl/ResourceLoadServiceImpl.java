package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ResourceLoadService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.ResourceLoadChart;
import com.webplusgd.aps.vo.ResourceLoadItem;
import optaplanner.domain.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rollingegg
 * @create_time 11/9/2020 11:13 PM
 */
@Service("ResourceLoadService")
public class ResourceLoadServiceImpl implements ResourceLoadService {
    private static final Integer GROUP_WORK_TIME = 12;
    private static final Integer MACHINE_WORK_TIME = 24;
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
        // <资源,每天工作时间列表>对应关系
        HashMap<Resource, List<Integer>> resourceWorkTime = new HashMap<>();
        for (ScheduledTask task : amongResult) {
            resourceWorkTime.putIfAbsent(task.getResource(), Arrays.asList(0, 0, 0, 0, 0, 0, 0));
            List<Integer> workTimeList = resourceWorkTime.get(task.getResource());
            int dayIndex = (int) DateUtil.daysDiff(task.getTimeslot().getStartDateTime(), startDateTime);
            workTimeList.set(dayIndex, (int) (workTimeList.get(dayIndex) + task.getTimeslot().getDurationOfHours()));
        }
        List<ResourceLoadItem> resourceLoadItemList = new ArrayList<>();
        int humanWorkTime = 0, humanTime = 0;
        int machineWorkTime = 0, machineTime = 0;
        for (Map.Entry<Resource, List<Integer>> entry : resourceWorkTime.entrySet()) {
            Resource currentResource = entry.getKey();
            List<Integer> workTime = entry.getValue();
            List<Double> rates = new ArrayList<>();
            if ("Group".equals(currentResource.getType())) {
                rates = workTime.stream().map(o -> o * 1.0 / (currentResource.getCapacity() * GROUP_WORK_TIME)).collect(Collectors.toList());
                humanWorkTime += workTime.stream().mapToInt(t -> t).sum();
                humanTime += currentResource.getCapacity() * GROUP_WORK_TIME * 7;
            } else {
                rates = workTime.stream().map(o -> o * 1.0 / (currentResource.getCapacity() * MACHINE_WORK_TIME)).collect(Collectors.toList());
                machineWorkTime += workTime.stream().mapToInt(t -> t).sum();
                machineTime += currentResource.getCapacity() * MACHINE_WORK_TIME * 7;
            }
            resourceLoadItemList.add(new ResourceLoadItem(DateUtil.date2LocalDate(startDate), currentResource.getName(), rates));
        }
        return new ResourceLoadChart(humanWorkTime * 1.0 / humanTime,
                machineWorkTime * 1.0 / machineTime, DateUtil.date2LocalDate(startDate), resourceLoadItemList);
    }
}
