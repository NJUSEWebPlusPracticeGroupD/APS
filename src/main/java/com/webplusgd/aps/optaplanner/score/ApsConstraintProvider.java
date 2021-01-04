package com.webplusgd.aps.optaplanner.score;

import com.webplusgd.aps.optaplanner.domain.Product;
import com.webplusgd.aps.optaplanner.domain.Task;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.List;

import static com.webplusgd.aps.optaplanner.score.ApsConstraintConfiguration.ORDER_OVERTIME_LOW;
import static com.webplusgd.aps.optaplanner.score.ApsConstraintConfiguration.RESOURCE_LOAD_HIGH;

/**
 * @author Rollingegg
 * @create_time 1/4/2021 1:26 PM
 */
public class ApsConstraintProvider implements ConstraintProvider {
    /**
     * HardScore
     */
    private static final String PRODUCT_UNAVAILABLE_RESOURCE = "Product unavailable resource";
    private final HardSoftScore productUnavailableResourcePenalize = HardSoftScore.ofHard(3);
    private static final String PRODUCT_MINIMUM_CAPACITY = "Product's resource reach minimum";
    private final HardSoftScore productMinimumCapacityPenalize = HardSoftScore.ofHard(2);

    /**
     * SoftScore
     */
    public static final String REDUNDANT_RESOURCE = "Product allocated redundant resource";
    private final HardSoftScore redundantResourcePenalize = HardSoftScore.ofSoft(2);
    public static final String SCHEDULE_FLATTEN = "Schedule as long as possible";
    private final HardSoftScore scheduleFlattenAward = HardSoftScore.ofSoft(2);
    public static final String RESOURCE_SHIFT_BALANCE = "Resource's shift balanced";
    private final HardSoftScore resourceShiftBalancePenalize = HardSoftScore.ofSoft(2);

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                // Hard constraints
                productUnavailableResource(constraintFactory),
//                productMinimumCapacity(constraintFactory),
                // Soft constraints
//                redundantResource(constraintFactory),
                orderOvertimeLow(constraintFactory),
//                resourceLoadHigh(constraintFactory)
        };
    }

    // ************************************************************************
    // Hard constraints
    // ************************************************************************

    protected Constraint productUnavailableResource(ConstraintFactory factory) {
        return factory.from(Task.class)
                .filter(Task::hasUnavailableResource)
                .penalize(PRODUCT_UNAVAILABLE_RESOURCE, productUnavailableResourcePenalize);
    }

    protected Constraint productMinimumCapacity(ConstraintFactory factory) {
        return factory.from(Task.class)
                .groupBy(Task::getOrder, Task::getTimeslot, ConstraintCollectors.sum(Task::getGroupMemberCount))
                .filter((order, timeslot, totalOfHuman) -> totalOfHuman < order.getRequiredPeople())
                .penalize(PRODUCT_MINIMUM_CAPACITY, productMinimumCapacityPenalize);
    }

    // ************************************************************************
    // Soft constraints
    // ************************************************************************

    protected Constraint redundantResource(ConstraintFactory factory) {
        return factory.from(Task.class)
                .groupBy(Task::getOrder, Task::getTimeslot, ConstraintCollectors.toList(Task::getResource))
                .filter(((order, timeslot, resources) -> Product.isRedundantArrange(resources, order.getProduct())))
                .penalize(REDUNDANT_RESOURCE, redundantResourcePenalize);
    }

    protected Constraint orderOvertimeLow(ConstraintFactory factory) {
        return factory.from(Task.class)
                .filter(Task::ddlExceed)
                .groupBy(Task::getOrder, ConstraintCollectors.count())
                .penalizeConfigurable(ORDER_OVERTIME_LOW, ((order, integer) -> 1));
    }

    protected Constraint resourceLoadHigh(ConstraintFactory factory) {
        return factory.from(Task.class)
                .groupBy(Task::getResource, ConstraintCollectors.count())
                .penalizeConfigurable(RESOURCE_LOAD_HIGH, (resource, noWorkTime) -> noWorkTime);
    }

    public static boolean hasExceededOrder(List<Task> taskList){
        for(Task task:taskList){
            if(task.ddlExceed()){
                return true;
            }
        }
        return false;
    }
}
