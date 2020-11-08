package com.webplusgd.aps.optaplanner;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Rollingegg
 */
public interface Planner {
    /**
     * 获取排程计划
     * @param currentTime 当前时间
     * @return 排程计划List
     */
    public List<ScheduledTask> getPlan(Timestamp currentTime);
}
