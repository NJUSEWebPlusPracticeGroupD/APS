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
        if (null != availableGroupResource && null != availableMachineResource && availableMachineResource.size()>0) {
            // 所需人力资源不为空且设备也不为空
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
        return false;
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

    /**
     * 优化现有生产安排，避免冗余资源
     *
     * @param groupResourceList 已安排/候选的人力资源
     * @param machineResourceList  已安排/候选的设备资源
     * @param product          指定的工艺路线
     * @param production        目标产能
     */
    public static void optimizeArrange(List<GroupResource> groupResourceList,
                                       List<MachineResource> machineResourceList,
                                       Product product, int production) {

        // 基于产能产量进行资源排程安排
        int humanCapacity = (int) Math.ceil(production * 1.0 / product.getStandardCapacity()) * product.getMinimumStaff();
        int lineNum = (int) Math.ceil(production * 1.0 / product.getStandardCapacity());


        int tempHumanCapacity = 0;
        int len;
        int index;
        for (index = 0; index < groupResourceList.size(); index++) {
            if (tempHumanCapacity >= humanCapacity) {
                break;
            }
            tempHumanCapacity += groupResourceList.get(index).getCapacity();
        }
        len = groupResourceList.size();
        groupResourceList.removeAll(groupResourceList.subList(index, len));
        for (int i = 0; i < groupResourceList.size(); i++) {
            if (tempHumanCapacity - groupResourceList.get(i).getCapacity() >= humanCapacity) {
                tempHumanCapacity -= groupResourceList.get(i).getCapacity();
                groupResourceList.remove(i);
                i--;
            }
        }

        len = machineResourceList.size();
        machineResourceList.removeAll(machineResourceList.subList(lineNum, len));
    }
}
