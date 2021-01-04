package com.webplusgd.aps.optaplanner.optional;

import com.webplusgd.aps.optaplanner.domain.Task;

import java.util.Comparator;

/**
 * @author Rollingegg
 * @create_time 11/11/2020 5:35 AM
 * ddl越早越重要
 */
public class TaskDifficultyComparator implements Comparator<Task> {
    @Override
    public int compare(Task t1, Task t2) {
        return t2.getTimeslot().getEndDateTime().compareTo(t1.getTimeslot().getEndDateTime());
    }
}
