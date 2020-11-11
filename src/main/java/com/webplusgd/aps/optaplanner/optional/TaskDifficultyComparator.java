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
    public int compare(Task o1, Task o2) {
        return o2.getOrder().getTermOfDeliver().compareTo(o1.getOrder().getTermOfDeliver());
    }
}
