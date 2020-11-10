package optaplanner.domain.resource;

import optaplanner.domain.Shift;
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
    private int capacity;
    protected String name;
    protected Shift shift;
    protected String type="undefined";

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Resource && name.equals(((Resource) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
