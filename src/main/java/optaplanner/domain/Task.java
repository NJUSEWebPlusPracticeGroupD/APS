package optaplanner.domain;

import optaplanner.domain.resource.GroupResource;
import optaplanner.domain.resource.MachineResource;
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

    @PlanningVariable(valueRangeProviderRefs = "groupRange")
    private GroupResource groupResource;

    @PlanningVariable(valueRangeProviderRefs = "machineRange")
    private MachineResource machineResource;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    private Timeslot timeslot;

    public int getId() {
        return order.getOrderId();
    }

    public boolean hasResourceShiftConflict() {
        return Shift.shiftTimeslotConflict(groupResource.getShift(), timeslot)
                || Shift.shiftTimeslotConflict(machineResource.getShift(), timeslot);
    }

    public boolean hasBomResourceConflict() {
        Product productBom = order.getProduct();
        return !(productBom.getAvailableGroupResource().contains(groupResource)
                && productBom.getAvailableMachineResource().contains(machineResource));
    }
}
