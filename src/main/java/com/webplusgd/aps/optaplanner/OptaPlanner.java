package com.webplusgd.aps.optaplanner;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Rollingegg
 */
@Component
public class OptaPlanner implements Planner{
    @Override
    public List<ScheduledTask> getPlan(Timestamp currentTime) {
        return null;
    }
}
