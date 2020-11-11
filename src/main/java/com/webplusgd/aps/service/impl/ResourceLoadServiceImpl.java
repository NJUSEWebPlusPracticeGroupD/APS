package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.exception.NoPlanException;
import com.webplusgd.aps.optaplanner.FCFSPlanner;
import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ResourceLoadService;
import com.webplusgd.aps.utils.DateUtil;
import com.webplusgd.aps.vo.ResourceLoadChart;
import com.webplusgd.aps.vo.ResourceLoadItem;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
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
    private static final Integer DAY_RANGE_LENGTH = 7;
    @Autowired
    private FCFSPlanner planner;

    @Override
    public ResourceLoadChart getResourceLoadChart(Date startDate) throws NoPlanException {
        // TODO 返回资源负载图
        List<ScheduledTask> output = planner.tryGetPlan();
        if (output == null) {
            throw new NoPlanException("获取资源负载图失败");
        }
        // 从指定日期的7点开始计算(边界值取-1s/+1s)
        LocalDateTime startDateTime = DateUtil.date2LocalDateTime(startDate).plusHours(7).minusSeconds(1);
        LocalDateTime endDateTime = startDateTime.plusDays(DAY_RANGE_LENGTH).plusSeconds(2);
        // 取出指定日期一周内的生产任务
        List<ScheduledTask> amongResult = output.stream().filter(o -> o.getTimeslot().getStartDateTime().isAfter(startDateTime)
                && endDateTime.isAfter(o.getTimeslot().getEndDateTime()))
                .collect(Collectors.toList());
        // <资源,每天工作时间列表>对应关系
        HashMap<Resource, List<Integer>> resourceWorkTime = new HashMap<>();
        for (ScheduledTask task : amongResult) {
            resourceWorkTime.putIfAbsent(task.getResource(), Arrays.asList(0, 0, 0, 0, 0, 0, 0));
            List<Integer> workTimeList = resourceWorkTime.get(task.getResource());
            int dayIndex = (int) DateUtil.daysDiff(startDateTime, task.getTimeslot().getStartDateTime());
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
                // 资源负载率=实际工作时间/可工作时间
                rates = workTime.stream().map(o -> o * 1.0 / GROUP_WORK_TIME).collect(Collectors.toList());
                humanWorkTime += workTime.stream().mapToInt(t -> t * currentResource.getCapacity()).sum();
                humanTime += currentResource.getCapacity() * GROUP_WORK_TIME * DAY_RANGE_LENGTH;
            } else {
                rates = workTime.stream().map(o -> o * 1.0 / MACHINE_WORK_TIME).collect(Collectors.toList());
                machineWorkTime += workTime.stream().mapToInt(t -> t * currentResource.getCapacity()).sum();
                machineTime += currentResource.getCapacity() * MACHINE_WORK_TIME * DAY_RANGE_LENGTH;
            }
            resourceLoadItemList.add(new ResourceLoadItem(DateUtil.date2LocalDate(startDate), currentResource.getName(), rates));
        }
        return new ResourceLoadChart(humanWorkTime * 1.0 / humanTime,
                machineWorkTime * 1.0 / machineTime, DateUtil.date2LocalDate(startDate), resourceLoadItemList);
    }
}
