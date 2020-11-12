package com.webplusgd.aps.optaplanner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Rollingegg
 */
public interface Planner {
    /**
     * 启动排程，并且异步获取排程计划
     *
     * @param currentTime 当前时间
     * @return 排程计划List
     */
    List<ScheduledTask> getPlan(Timestamp currentTime);

    /**
     * 开启排程任务，排程计划另外获取
     * @param currentTime 排程启动时间
     */
    void startSchedule(LocalDateTime currentTime);

    /**
     * 异步获取排程计划
     * @return 排程计划List
     */
    List<ScheduledTask> tryGetPlan();

    /**
     * 立即获取排程计划
     * @return 排程计划List
     */
    List<ScheduledTask> waitForPlan();
}
