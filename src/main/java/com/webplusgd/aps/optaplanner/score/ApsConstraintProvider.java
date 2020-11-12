package com.webplusgd.aps.optaplanner.score;

import com.webplusgd.aps.optaplanner.domain.Task;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.*;

import java.util.Objects;

/**
 * @author Rollingegg
 * @create_time 11/4/2020 7:40 PM
 */
public class ApsConstraintProvider implements ConstraintProvider {

    private static final int BENDABLE_SCORE_HARD_LEVELS_SIZE = 1;
    private static final int BENDABLE_SCORE_SOFT_LEVELS_SIZE = 4;
    private static final int RESOURCE_SHIFT_CONFLICT_PENALIZE = 2;
    private static final int RESOURCE_UNIQUE_PENALIZE = 2;
    private static final int BOM_RESOURCE_CONFLICT_PENALIZE = 3;
    private static final int BOM_CAPACITY_CONFLICT_PENALIZE = 2;

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
//                groupUniqueConflict(constraintFactory),
//                machineUniqueConflict(constraintFactory),
//                noResourceShiftConflict(constraintFactory),
//                noBomResourceConflict(constraintFactory),
//                bomCapacityConflict(constraintFactory)
                machineNotRight(constraintFactory), groupNotRight(constraintFactory),
                useSameGroup(constraintFactory), groupMemberCountNotEnough(constraintFactory),
                groupCannotWork(constraintFactory),
                groupConflict(constraintFactory),
                machineConflict(constraintFactory), ddlExceed(constraintFactory)
        };
    }

    public Constraint groupUniqueConflict(ConstraintFactory constraintFactory) {
        // TODO 同一人力资源同一时刻只能做一个生产任务
        return constraintFactory.from(Task.class)
                .join(Task.class,
                        Joiners.equal(Task::getTimeslot),
                        Joiners.equal(Task::getGroupResource),
                        Joiners.lessThan(Task::getId)
                )
                .penalize("groupUniqueConflict", HardSoftScore.ONE_HARD);
    }


    public Constraint machineUniqueConflict(ConstraintFactory constraintFactory) {
        // TODO 同一机器资源同一时刻只能做一个生产任务
        return constraintFactory.from(Task.class)
                .join(Task.class,
                        Joiners.equal(Task::getTimeslot),
                        Joiners.equal(Task::getMachineResource),
                        Joiners.lessThan(Task::getId)
                )
                .penalize("machineUniqueConflict", HardSoftScore.ONE_HARD);
    }

    public Constraint noResourceShiftConflict(ConstraintFactory constraintFactory) {
        // TODO 时间段安排不能与资源的班次冲突
        return constraintFactory.from(Task.class)
                .filter(Task::hasResourceShiftConflict)
                .penalize("No resource's shift conflict", HardSoftScore.ONE_HARD);
    }

    public Constraint noBomResourceConflict(ConstraintFactory constraintFactory) {
        // TODO 人员和生产线必须是生产BOM指定的
        return constraintFactory.from(Task.class)
                .filter(Task::hasBomConflict)
                .penalize("All Resources obey bom", HardSoftScore.ONE_HARD);
    }

    public Constraint bomCapacityConflict(ConstraintFactory constraintFactory) {
        // TODO 同一订单安排的生产单的人员能力之和应大于工艺要求
        return constraintFactory.from(Task.class)
                .groupBy(Task::getOrder, Task::getTimeslot, ConstraintCollectors.sum(Task::getGroupCount))
                .filter((order, timeslot, totalOfHuman) -> totalOfHuman > order.getRequiredPeople())
                .penalize("Order meets quota", HardSoftScore.ONE_HARD,
                        (order, timeslot, totalOfHuman) -> totalOfHuman - order.getRequiredPeople());
    }

    /**
     * ==================== Other ========================
     */
    private Constraint machineNotRight(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Task.class).filter(Task::machineNotInBom).penalize("machineNotRight",
                HardSoftScore.ONE_HARD);
    }

    private Constraint groupNotRight(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Task.class).penalize("groupNotRight", HardSoftScore.ONE_HARD,
                Task::getGroupCount);
    }

    private Constraint useSameGroup(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Task.class).penalize("useSameGroup", HardSoftScore.ONE_HARD,
                Task::getSameGroupCount);
    }

    private Constraint groupMemberCountNotEnough(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Task.class).penalize("groupMemberCountNotEnough", HardSoftScore.ONE_HARD,
                Task::memberCountNotEnoughCount);
    }

    private Constraint groupCannotWork(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Task.class).penalize("groupCannotWork", HardSoftScore.ONE_HARD,
                Task::groupCannotWorkCount);
    }

    private Constraint groupConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Task.class)
                .join(Task.class, Joiners.lessThan(Task::getId), Joiners.equal(Task::getTimeslot))
                .filter(Task::groupConflict).penalize("groupConflict", HardSoftScore.ONE_HARD);
    }

    private Constraint machineConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .from(Task.class).join(Task.class, Joiners.lessThan(Task::getId),
                        Joiners.equal(Task::getTimeslot), Joiners.equal(Task::getMachineResource))
                .penalize("machineConflict", HardSoftScore.ONE_HARD);
    }

    private Constraint ddlExceed(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Task.class).filter(Task::ddlExceed).penalize("ddlExceed",
                HardSoftScore.ONE_SOFT);
    }
}
