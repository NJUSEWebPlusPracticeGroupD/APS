package com.webplusgd.aps.optaplanner.domain.resource;

import com.webplusgd.aps.optaplanner.domain.Shift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 11:09 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    /**
     * 群组人数/机器数量，代表生产能力
     */
    protected int capacity;
    protected String name;
    protected Shift shift;
    protected String type=UNDEFINED_TYPE;

    public final static String UNDEFINED_TYPE = "Undefined";
    public final static String GROUP_TYPE = "Group";
    public final static String MACHINE_TYPE = "Machine";
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Resource && name.equals(((Resource) obj).name);
    }


    @Override
    public String toString() {
        return name;
    }
}
