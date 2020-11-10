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
@NoArgsConstructor
public class MachineResource extends Resource{
    public MachineResource(int capacity, String name, Shift shift) {
        super(capacity, name, shift, "Machine");
    }
}
