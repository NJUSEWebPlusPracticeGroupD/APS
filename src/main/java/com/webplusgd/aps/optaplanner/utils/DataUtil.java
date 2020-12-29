package com.webplusgd.aps.optaplanner.utils;

import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rollingegg
 * @create_time 12/25/2020 8:29 PM
 * 负责task、order等数据集的形式变换及通用计算
 */
public class DataUtil {

    /**
     * 将每个订单每道工序的产量按照订单Id进行聚合
     *
     * @param orderList
     * @return
     */
    public static Map<Integer, List<Integer>> groupOrderAmountByOrderIdAndStep(List<Order> orderList) {
        // 订单不同工序的剩余数量
        Map<Integer, List<Integer>> result = new HashMap<>();
        int id;
        List<Integer> tempStepRest;
        for (Order order : orderList) {
            id = order.getOrderId();
            if (!result.containsKey(id)) {
                result.put(id, new ArrayList<>());
            }
            tempStepRest = result.get(id);
            while (tempStepRest.size() < order.getProduct().getStep()) {
                tempStepRest.add(0);
            }
            tempStepRest.set(order.getProduct().getStep() - 1, (int) order.getOrderNum());
        }
        //移除缺省订单
        result.remove(0);
        return result;
    }
    /**
     * 将订单关于订单id进行聚合
     *
     * @param orderList 聚合前的order列表
     * @return 聚合后的order列表
     */
    public static Map<Integer, List<Order>> groupOrderByOrderId(List<Order> orderList) {
        Map<Integer, List<Order>> result = new HashMap<>();
        int orderId;
        int step;
        List<Order> temp;
        for (Order order : orderList) {
            if (order.getOrderId() == 0) {
                continue;
            }
            orderId = order.getOrderId();
            if (!result.containsKey(orderId)) {
                result.put(orderId, new ArrayList<>());
            }
            step = order.getProduct().getStep();
            temp = result.get(orderId);
            if (temp.size() < step) {
                temp.add(null);
            }
            temp.set(step - 1, order);
        }
        return result;
    }

    /**
     * 将计划单元按照订单id进行聚合
     *
     * @param taskList 聚合前的排程计划
     * @return 聚合后的排程计划
     */
    public static Map<Integer, List<Task>> groupTaskByOrderId(List<Task> taskList) {
        Map<Integer, List<Task>> result = new HashMap<>();
        int orderId;
        for (Task task : taskList) {
            if (task.hasNull() || task.getOrder().getOrderId() == 0) {
                continue;
            }
            orderId = task.getOrder().getOrderId();
            if (!result.containsKey(orderId)) {
                result.put(orderId, new ArrayList<>());
            }
            result.get(orderId).add(task);
        }
        return result;
    }
    /**
     * 将计划单元按照时间和资源进行组织，获得每个工时每个资源的排程计划
     *
     * @param taskList 聚合前的排程计划
     * @return 聚合后的排程计划
     */
    public static Map<Timeslot, Map<Resource, Task>> groupTaskByTimeslotAndResource(List<Task> taskList) {
        Map<Timeslot, Map<Resource, Task>> result = new HashMap<>();
        for (Task task : taskList) {
            if (!result.containsKey(task.getTimeslot())) {
                result.put(task.getTimeslot(), new HashMap<>());
            }
            result.get(task.getTimeslot()).put(task.getResource(), task);
        }
        return result;
    }
    /**
     * 剔除不完整的或包含缺省订单的计划单元
     * 对计划单元按照时间进行排序
     *
     * @param taskList 排程过程中的计划单元列表
     * @return 清洗整理过后的计划结果
     */
    public static List<Task> toResult(List<Task> taskList) {
        return taskList.stream()
                .filter(s -> !(s.hasNull() || s.getOrder().getOrderId() == 0))
                .sorted(Comparator.comparing(o -> o.getTimeslot().getStartDateTime()))
                .collect(Collectors.toList());
    }
}
