package com.webplusgd.aps.optaplanner.domain;

import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import com.webplusgd.aps.optaplanner.optional.TaskDifficultyComparator;
//import liquibase.pro.packaged.I;
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
@PlanningEntity(difficultyComparatorClass = TaskDifficultyComparator.class)
@Data
@NoArgsConstructor
public class Task {
    //    @PlanningVariable(valueRangeProviderRefs = "orderRange")
    private Order order;
    private String id;
    private Integer remainHours;

    @PlanningVariable(valueRangeProviderRefs = "groupRange")
    private GroupResource groupResource;

    @PlanningVariable(valueRangeProviderRefs = "groupRange", nullable = true)
    private GroupResource anotherGroupResource;

    @PlanningVariable(valueRangeProviderRefs = "machineRange")
    private MachineResource machineResource;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    private Timeslot timeslot;

    public Task(Order order, String id, int remainHours) {
        this.order = order;
        this.id = id;
        this.remainHours=remainHours;
    }

    public boolean hasResourceShiftConflict() {
        return (groupResource != null && machineResource != null) && (Shift.shiftTimeslotConflict(groupResource.getShift(), timeslot)
                || Shift.shiftTimeslotConflict(machineResource.getShift(), timeslot));
    }

    public boolean hasBomConflict() {
        Product productBom = order.getProduct();
        return (groupResource != null && machineResource != null) && !(productBom.getAvailableGroupResource().contains(groupResource)
                && productBom.getAvailableMachineResource().contains(machineResource));
    }

    public boolean isEmpty() {
        return order == null && groupResource == null && machineResource == null && timeslot == null;
    }

    public boolean hasNull() {
        return order == null || groupResource == null || machineResource == null || timeslot == null;
    }

    public int getGroupCount() {
        return groupResource == null ? 0 : groupResource.getCapacity();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", orderId=" + order.getOrderId() +
                ", remainHours=" + remainHours +
                ", groupResource=" + groupResource +
                ", anotherGroupResource=" + anotherGroupResource +
                ", machineResource=" + machineResource +
                ", timeslot=" + timeslot +
                '}';
    }

    /**
     * ======================== Other =================================
     *
     * @return machine是否满足Bom分配
     */
    public boolean machineNotInBom() {
        Product productBom = order.getProduct();
        return machineResource != null && productBom.getAvailableMachineResource().contains(machineResource);
    }

    public int getGroupNotInBomCount() {
        Product productBom = order.getProduct();
        int res = 0;
        if (groupResource != null && !productBom.getAvailableGroupResource().contains(groupResource)) {
            res++;
        }
        if (anotherGroupResource != null && !productBom.getAvailableGroupResource().contains(anotherGroupResource)) {
            res++;
        }
        return 2 * res;
    }

    public int getSameGroupCount() {
        int res = 0;
        if (groupResource != null && anotherGroupResource == groupResource) {
            res++;
        }
        return res;
    }

    public int memberCountNotEnoughCount() {
        int count = 0;
        if (groupResource != null) {
            count += groupResource.getCapacity();
        }
        if (anotherGroupResource != null) {
            count += anotherGroupResource.getCapacity();
        }
        if (count < order.getRequiredPeople()) {
            return order.getRequiredPeople() - count;
        }
        return 0;
    }

    public int groupCannotWorkCount() {
        int count = 0;
        if (groupResource != null && Shift.shiftTimeslotConflict(groupResource.getShift(), timeslot)) {
            count++;
        }
        if (anotherGroupResource != null && Shift.shiftTimeslotConflict(anotherGroupResource.getShift(), timeslot)) {
            count++;
        }
        return count;
    }

    public boolean ddlExceed() {
        return timeslot != null && timeslot.getEndDateTime().isAfter(order.getTermOfDeliver());
    }

    public boolean groupConflict(Task other) {
        boolean res = false;
        if (groupResource != null) {
            res = (groupResource == other.groupResource);
        }
        if (machineResource != null) {
            res |= (machineResource == other.machineResource);
        }
        return res;
    }
}
