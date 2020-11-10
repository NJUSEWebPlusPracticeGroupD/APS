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
    protected String name;
    protected Shift shift;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Resource && name.equals(((Resource) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getType(){
        return "undefined";
    }
}
