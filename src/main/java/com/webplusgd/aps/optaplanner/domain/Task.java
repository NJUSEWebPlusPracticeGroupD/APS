package com.webplusgd.aps.optaplanner.domain;

import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import com.webplusgd.aps.optaplanner.optional.TaskDifficultyComparator;
import liquibase.pro.packaged.I;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * @author Rollingegg
 * @create_time 11/6/2020 11:06 AM
 * 分配实体
 */
@PlanningEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @PlanningVariable(valueRangeProviderRefs = "orderRange")
    private Order order;
    private Resource resource;

    private Timeslot timeslot;

    public boolean isEmpty() {
        return order == null && resource == null && timeslot == null;
    }

    public boolean hasNull() {
        return order == null || resource == null || timeslot == null;
    }

}
