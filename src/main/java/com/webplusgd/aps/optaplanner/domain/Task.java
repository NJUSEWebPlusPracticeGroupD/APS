package com.webplusgd.aps.optaplanner.domain;

import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import com.webplusgd.aps.optaplanner.optional.OrderStrengthComparator;
import com.webplusgd.aps.optaplanner.optional.TaskDifficultyComparator;
//import liquibase.pro.packaged.I;
import com.webplusgd.aps.optaplanner.score.ApsConstraintConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.List;

/**
 * @author Rollingegg
 * @create_time 11/6/2020 11:06 AM
 * 分配实体
 */
@PlanningEntity(difficultyComparatorClass = TaskDifficultyComparator.class)
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

    public boolean hasUnavailableResource() {
        if (order == null || order.getOrderId() == 0) {
            return false;
        }
        Product product = order.getProduct();
        List<GroupResource> candidateHuman = product.getAvailableGroupResource();
        List<MachineResource> candidateLine = product.getAvailableMachineResource();
        if (Resource.GROUP_TYPE.equals(resource.getType())) {
            // 是否需要人力资源 || 是否属于候选资源
            return !(null != candidateHuman && candidateHuman.contains((GroupResource) resource));
        } else if (Resource.MACHINE_TYPE.equals(resource.getType())) {
            // 是否需要设备资源 || 是否属于候选资源
            return !(null != candidateLine && candidateLine.contains((MachineResource) resource));
        }
        return false;
    }

    public Integer getGroupMemberCount() {
        return Resource.GROUP_TYPE.equals(resource.getType()) ? resource.getCapacity() : 0;
    }

    public boolean ddlExceed() {
        return order!=null&&order.getOrderId() != 0 && timeslot.getEndDateTime().isAfter(order.getTermOfDeliver());
    }
}
