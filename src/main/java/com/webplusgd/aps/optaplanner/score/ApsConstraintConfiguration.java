package com.webplusgd.aps.optaplanner.score;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

/**
 * @author Rollingegg
 * @create_time 1/4/2021 1:28 PM
 */
@ConstraintConfiguration(constraintPackage = "com.webplusgd.aps.optaplanner.score")
@Data
@NoArgsConstructor
public class ApsConstraintConfiguration {
    /**
     * SoftScore
     */
    public static final String RESOURCE_LOAD_HIGH="Resource's load as high as possible";
    public static final String ORDER_OVERTIME_LOW="Order overtime as low as possible";


    /**
     * 资源负载率权重默认初始化为1
     * 订单逾期权重默认初始化为10
     */
    @ConstraintWeight(RESOURCE_LOAD_HIGH)
    private HardSoftScore resourceLoadHigh=HardSoftScore.ONE_SOFT;
    @ConstraintWeight(ORDER_OVERTIME_LOW)
    private HardSoftScore orderOvertimeLow=HardSoftScore.ofSoft(10);

}
