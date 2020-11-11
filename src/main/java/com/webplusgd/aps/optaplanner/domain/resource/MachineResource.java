package com.webplusgd.aps.optaplanner.domain.resource;

import lombok.NoArgsConstructor;
import com.webplusgd.aps.optaplanner.domain.Shift;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 12:46 PM
 */
@NoArgsConstructor
public class MachineResource extends Resource{
    public MachineResource(int capacity, String name, Shift shift) {
        super(capacity, name, shift, "Machine");
    }
}
