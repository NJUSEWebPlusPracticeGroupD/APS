package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Rollingegg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTask implements Cloneable, Serializable {
    private Order order;

    private Resource resource;

    private Timeslot timeslot;

    /**
     * 工艺路线的步骤
     */
    private int step;

    public Object clone() {
        ScheduledTask scheduledTask = null;
        try {
            scheduledTask = (ScheduledTask) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scheduledTask;
    }

}
