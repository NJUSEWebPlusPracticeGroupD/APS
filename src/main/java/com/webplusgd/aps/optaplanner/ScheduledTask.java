package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rollingegg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTask {
    private Order order;

    private Resource resource;

    private Timeslot timeslot;

    /**
     * 工艺路线的步骤
     */
    private int step;
}
