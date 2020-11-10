package optaplanner.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import optaplanner.domain.Shift;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 12:45 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResource extends Resource{
    /**
     * 群组人数，代表生产能力
     */
    private int capacity;

    @Override
    public String getType() {
        return "Group";
    }
}
