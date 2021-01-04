package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import com.webplusgd.aps.optaplanner.score.ApsConstraintConfiguration;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;
import java.util.Map;

/**
 * @author Rollingegg
 * @create_time 11/5/2020 11:20 AM
 */
@PlanningSolution
@Data
@NoArgsConstructor
public class ApsSolution {
    @ConstraintConfigurationProvider
    private ApsConstraintConfiguration apsConstraintConfiguration;

    @ValueRangeProvider(id = "orderRange")
    @ProblemFactCollectionProperty
    private List<Order> orderList;

    private List<Resource> resourceList;
    private List<Timeslot> timeslotList;
    private Map<Integer, List<Order>> orderMap;

    @PlanningEntityCollectionProperty
    private List<Task> taskList;

    @PlanningScore
    private HardSoftScore score;

    public ApsSolution(List<Order> orderList, List<Resource> resourceList, List<Timeslot> timeslotList, Map<Integer, List<Order>> orderMap, List<Task> taskList) {
        this.orderList = orderList;
        this.resourceList = resourceList;
        this.timeslotList = timeslotList;
        this.orderMap = orderMap;
        this.taskList = taskList;
    }

    public ApsSolution withConstraintConfiguration(ApsConstraintConfiguration constraintConfiguration){
        this.apsConstraintConfiguration=constraintConfiguration;
        return this;
    }
}
