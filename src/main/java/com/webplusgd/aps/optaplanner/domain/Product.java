package com.webplusgd.aps.optaplanner.domain;

import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 12:35 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer materialId;
    private List<GroupResource> availableGroupResource;
    private List<MachineResource> availableMachineResource;
    private int standardCapacity;
    private int minimumStaff;
    private int step;

    public boolean isValid() {
        if (null != availableGroupResource) { // 所需人力资源不为空
            Map<LocalTime, Integer> shiftCount = new HashMap<>();
            Shift shift;
            for (GroupResource resource : availableGroupResource) {
                shift = resource.getShift();
                if (!shiftCount.containsKey(shift.getStartTime())) {
                    shiftCount.put(shift.getStartTime(), 0);
                }
                shiftCount.put(shift.getStartTime(), shiftCount.get(shift.getStartTime()) + resource.getCapacity());
            }
            for (Integer count : shiftCount.values()) {
                if (count >= minimumStaff) {
                    // 存在能满足人力需求的时段
                    return true;
                }
            }
            return false;
        }
        // 人力资源为空的情况下，设备也为空则工艺路线无效
        return null != availableMachineResource;
    }

    /**
     * 计算当前分配情况的最大产量
     *
     * @param humanCapacity 分配的人力资源量
     * @param lineNum       分配的产线数量
     * @param product      工艺路线
     * @return 产量
     */
    public static int calculateProduction(int humanCapacity, int lineNum, Product product) {
        int production;
        if (null == product.getAvailableGroupResource()) {
            production = lineNum * product.getStandardCapacity();
        } else if (null == product.getAvailableMachineResource()) {
            production = humanCapacity / product.getMinimumStaff() * product.getStandardCapacity();
        } else {
            production = Math.min(lineNum, humanCapacity / product.getMinimumStaff()) * product.getStandardCapacity();
        }
        return production;
    }

    public static boolean isRedundantArrange(List<GroupResource> groupResourceList,
                                             List<MachineResource> machineResourceList,
                                             Product product) {
        int humanCapacity = 0;
        for (GroupResource groupResource : groupResourceList) {
            humanCapacity += groupResource.getCapacity();
        }
        int lineNum = machineResourceList.size();
        int production = calculateProduction(humanCapacity, lineNum, product);
        if (null == product.getAvailableGroupResource()) {
            // 由于设备制造的产能一般按照设备数量计算，所以不太会有冗余情况，均能充分利用
            return false;
        } else {
            for (GroupResource groupResource : groupResourceList) {
                // 若剔除该资源仍能获得相当的产量，则人力冗余
                if (production == calculateProduction(humanCapacity - groupResource.getCapacity(), lineNum, product)) {
                    return true;
                }
            }
            if (null != product.getAvailableMachineResource()) {
                // 若少一条产线也可以获得相当的产量，则产线冗余
                return (lineNum - 1) * product.getStandardCapacity() > production;
            }
        }
        return false;
    }
}
