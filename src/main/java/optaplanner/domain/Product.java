package optaplanner.domain;

import optaplanner.domain.resource.GroupResource;
import optaplanner.domain.resource.MachineResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 12:35 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String materialId;
    private List<GroupResource> availableGroupResource;
    private List<MachineResource> availableMachineResource;
    private int standardCapacity;
    private int minimumStaff;
}
