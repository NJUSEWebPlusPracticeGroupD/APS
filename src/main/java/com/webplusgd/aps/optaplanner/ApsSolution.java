package com.webplusgd.aps.optaplanner;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

/**
 * @author Rollingegg
 * @create_time 11/5/2020 11:20 AM
 */
@PlanningSolution
@Data
@NoArgsConstructor
public class ApsSolution {
//    @ValueRangeProvider(id = "orderRange")
//    @ProblemFactCollectionProperty
    private List<Order> orderList;

    @ValueRangeProvider(id = "groupRange")
    @ProblemFactCollectionProperty
    private List<GroupResource> groupResourceList;

    @ValueRangeProvider(id = "machineRange")
    @ProblemFactCollectionProperty
    private List<MachineResource> machineResourceList;

    @ValueRangeProvider(id = "timeslotRange")
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslotList;

    @PlanningEntityCollectionProperty
    private List<Task> taskList;

    @PlanningScore
    private HardSoftScore score;

    public ApsSolution(List<Order> orderList, List<GroupResource> groupResourceList, List<MachineResource> machineResourceList, List<Timeslot> timeslotList, List<Task> taskList) {
        this.orderList = orderList;
        this.groupResourceList = groupResourceList;
        this.machineResourceList = machineResourceList;
        this.timeslotList = timeslotList;
        this.taskList = taskList;
    }
}
