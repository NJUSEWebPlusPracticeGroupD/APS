package optaplanner.solver.score;

import optaplanner.domain.Task;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

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
                resourceUniqueConflict(constraintFactory),
                noResourceShiftConflict(constraintFactory)
        };
    }

    public Constraint resourceUniqueConflict(ConstraintFactory constraintFactory) {
        // TODO 同一资源同一时刻只能做一个生产任务
        return constraintFactory.from(Task.class)
                .join(Task.class,
                        Joiners.equal(Task::getTimeslot),
                        Joiners.equal(Task::getGroupResource),
                        Joiners.lessThan(Task::getId)
                )
                .penalize("resourceUniqueConflict",
                        BendableScore.ofHard(BENDABLE_SCORE_HARD_LEVELS_SIZE, BENDABLE_SCORE_SOFT_LEVELS_SIZE, 0, RESOURCE_UNIQUE_PENALIZE));
    }

    public Constraint noResourceShiftConflict(ConstraintFactory constraintFactory) {
        // TODO 时间段安排不能与资源的班次冲突
        return constraintFactory.from(Task.class)
                .filter(Task::hasResourceShiftConflict)
                .penalize("No resource's shift conflict",
                        BendableScore.ofHard(BENDABLE_SCORE_HARD_LEVELS_SIZE, BENDABLE_SCORE_SOFT_LEVELS_SIZE, 0, RESOURCE_SHIFT_CONFLICT_PENALIZE));
    }

    public Constraint noBomResourceConflict(ConstraintFactory constraintFactory) {
        // TODO 人员和生产线必须是生产BOM指定的
        return constraintFactory.from(Task.class)
                .filter(Task::hasBomResourceConflict)
                .penalize("All Resources obey bom",
                        BendableScore.ofHard(BENDABLE_SCORE_HARD_LEVELS_SIZE, BENDABLE_SCORE_SOFT_LEVELS_SIZE, 0, BOM_RESOURCE_CONFLICT_PENALIZE));
    }

    public Constraint bomCapacityConflict(ConstraintFactory constraintFactory) {
        // TODO 同一订单安排的生产单的人员能力之和应大于工艺要求
        return null;
    }
}
