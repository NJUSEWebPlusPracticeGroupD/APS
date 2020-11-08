package optaplanner;

import optaplanner.domain.Order;
import optaplanner.domain.Task;
import optaplanner.domain.Timeslot;
import optaplanner.domain.resource.GroupResource;
import optaplanner.domain.resource.MachineResource;
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
public class ApsSolution {
    @ValueRangeProvider(id = "orderRange")
    @ProblemFactCollectionProperty
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
}
