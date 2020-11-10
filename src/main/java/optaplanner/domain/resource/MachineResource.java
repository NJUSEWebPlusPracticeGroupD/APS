package optaplanner.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import optaplanner.domain.Shift;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 12:46 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineResource extends Resource{
    /**
     * 机器生产线数量
     */
    private int amount;

    @Override
    public String getType() {
        return "Machine";
    }
}
