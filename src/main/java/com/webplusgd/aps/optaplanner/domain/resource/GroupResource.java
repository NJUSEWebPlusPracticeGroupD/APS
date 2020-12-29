package com.webplusgd.aps.optaplanner.domain.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.webplusgd.aps.optaplanner.domain.Shift;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 12:45 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GroupResource extends Resource{
    public GroupResource(int capacity, String name, Shift shift) {
        super(capacity, name, shift, GROUP_TYPE);
    }
}
