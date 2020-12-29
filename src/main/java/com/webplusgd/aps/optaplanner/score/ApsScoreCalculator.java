package com.webplusgd.aps.optaplanner.score;

import com.webplusgd.aps.optaplanner.ApsSolution;
import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Product;
import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import com.webplusgd.aps.optaplanner.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Rollingegg
 * @create_time 12/27/2020 4:28 PM
 */
@Slf4j
public class ApsScoreCalculator implements EasyScoreCalculator<ApsSolution> {
    // HardScore
    private static final int CANNOT_FINISH_PENALIZE = 10;//产量必须合格，订单须完成
    private static final int PRODUCT_RESOURCE_CONFLICT_PENALIZE = 3;// 资源必须是生产工艺指定的
    private static final int PRODUCT_CAPACITY_CONFLICT_PENALIZE = 2;// 工时内订单被分配的资源数目应达到工艺要求
    private static final int REDUNDANT_RESOURCE = 2;// 同一工时对同一订单分配了多余的资源，并未提升产能
    private static final int STEP_CONFLICT_PENALIZE = 4;// 工序顺序冲突

    // SoftScore
    private static final int STEP_OVERFLOW_SOFT_PENALIZE = 1;// 后续工序的工时产量会受前序已有产量影响，最好一个工时产能拉满
    private static final int OVERTIME_PENALIZE = 5;// 订单逾期
    private static final int RESOURCE_BALANCE = 1; // 资源平衡使用，避免资源安排集中于有限的某几个资源
    private static final int KEEP_IN_TIME_STEP_PROCESS = 1;// todo 订单生产安排的集中or分散？

    @Override
    public HardSoftScore calculateScore(ApsSolution solution) {
        HardSoftScore score = HardSoftScore.of(0, 0);
        List<Task> taskList = solution.getTaskList();
        score = score.add(comprehensiveScoreCalculation(taskList, solution.getOrderMap()));
//        log.info(score.toString());
        return score;
    }

    /**
     * 所有评分在一次遍历中解决
     * @param taskList
     * @param orderMap
     * @return
     */
    private HardSoftScore comprehensiveScoreCalculation(List<Task> taskList, Map<Integer, List<Order>> orderMap) {
        int hardScore = 0;
        int softScore = 0;
        int[] score = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        Map<Integer, List<Task>> orderTaskMap = DataUtil.groupTaskByOrderId(taskList);
        List<Integer> count = new ArrayList<>();// 订单每道工序累计产量
        List<Integer> humanCapacity = new ArrayList<>();// 当前工时分配的人力
        List<Integer> lineNum = new ArrayList<>();// 当前工时分配的设备数量
        List<List<GroupResource>> humanArranged = new ArrayList<>();
        List<List<MachineResource>> lineArranged = new ArrayList<>();
        LocalDateTime current; // 用于工时记录和工时切换
        int step; // 记录工序
        int production; // 工时产量
        List<Task> list; // 订单的排程计划
        Task task;
        for (Integer orderId : orderMap.keySet()) {
            list = orderTaskMap.get(orderId);
            if (null == list) {
                hardScore -= CANNOT_FINISH_PENALIZE;
                score[0] += 1;
                continue;
            }
            list.sort(Comparator.comparing(o -> o.getTimeslot().getStartDateTime()));
            count.clear();
            humanCapacity.clear();
            lineNum.clear();
            while (count.size() < orderMap.get(orderId).size()) {
                count.add(0);
                humanArranged.add(new ArrayList<>());
                lineArranged.add(new ArrayList<>());
                humanCapacity.add(0);
                lineNum.add(0);
            }
            current = list.get(0).getTimeslot().getStartDateTime();
            // 遍历schedule列表，统计产量，检测资源安排
            for (int i = 0; i < list.size(); i++) {
                task = list.get(i);
                if (!isCandidateResource(task.getResource(), task.getOrder().getProduct())) {
                    hardScore -= PRODUCT_RESOURCE_CONFLICT_PENALIZE;
                    score[1] += 1;
                    continue;
                }
                if (task.getTimeslot().getEndDateTime().isAfter(task.getOrder().getTermOfDeliver())) {
                    softScore -= OVERTIME_PENALIZE;
                    score[6] += 1;
                }
                step = task.getOrder().getProduct().getStep();
                if (Resource.GROUP_TYPE.equals(task.getResource().getType())) {
                    humanArranged.get(step - 1).add((GroupResource) task.getResource());
                    humanCapacity.set(step - 1, humanCapacity.get(step - 1) + ((GroupResource) task.getResource()).getCapacity());
                } else if (Resource.MACHINE_TYPE.equals(task.getResource().getType())) {
                    lineArranged.get(step - 1).add((MachineResource) task.getResource());
                    lineNum.set(step - 1, lineNum.get(step - 1) + 1);
                }
                if (i == list.size() - 1 || !current.equals(list.get(i + 1).getTimeslot().getStartDateTime())) {
                    // 一个工时结束，对该工时的工作进行计算，工时切换
                    if (i < list.size() - 1) {
                        current = list.get(i + 1).getTimeslot().getStartDateTime();
                    }
                    for (int j = count.size() - 1; j >= 0; j--) {
                        production = Product.calculateProduction(humanCapacity.get(j), lineNum.get(j), orderMap.get(orderId).get(j).getProduct());
                        if (production == 0 && (humanCapacity.get(j) > 0 || lineNum.get(j) > 0)) {
                            // 该工时有资源安排，但是资源量不达标，无法生产
                            hardScore -= PRODUCT_CAPACITY_CONFLICT_PENALIZE;
                            score[2] += 1;
                            continue;
                        }
                        if (Product.isRedundantArrange(humanArranged.get(j), lineArranged.get(j), orderMap.get(orderId).get(j).getProduct())) {
                            // 冗余资源，扣分
                            hardScore -= REDUNDANT_RESOURCE;
                            score[3] += 1;
                        }
                        // 根据前后工序产量约束计算评分
                        if (j > 0) {
                            if (count.get(j).equals(count.get(j - 1))) {
                                if (production > 0) {
                                    // 先前工序没有产量而该工序有安排
                                    hardScore -= STEP_CONFLICT_PENALIZE;
                                    score[4] += 1;
                                }
                            } else {
                                if (count.get(j) + production > count.get(j - 1)) {
                                    // 有工序安排且工序产量溢出，最好不溢出
                                    softScore -= STEP_OVERFLOW_SOFT_PENALIZE;
                                    score[5] += 1;
                                }
                            }
                        }
                        // 更新工时产量
                        if (j == 0) {
                            // 第一道工序产量无前序工序约束
                            count.set(j, count.get(j) + production);
                        } else {
                            count.set(j, Math.min(count.get(j - 1), count.get(j) + production));
                        }
                        humanCapacity.set(j, 0);
                        humanArranged.get(j).clear();
                        lineNum.set(j, 0);
                        lineArranged.get(j).clear();
                    }
                }
            }
            for (int i = 0; i < count.size(); i++) {
                if (count.get(i) < orderMap.get(orderId).get(i).getOrderNum()) {
                    hardScore -= CANNOT_FINISH_PENALIZE;
                    score[0] += 1;
//                    log.info(orderId + " " + i);
                }
            }
        }
//        log.info(Arrays.toString(score));
        return HardSoftScore.of(hardScore, softScore);
    }

    /**
     * 判断资源是否符合工艺路线要求
     * @param resource 资源
     * @param product 对应产品
     * @return 是否符合
     */
    private Boolean isCandidateResource(Resource resource, Product product) {
        List<GroupResource> candidateHuman = product.getAvailableGroupResource();
        List<MachineResource> candidateLine = product.getAvailableMachineResource();
        if (Resource.GROUP_TYPE.equals(resource.getType())) {
            // 是否需要人力资源 || 是否属于候选资源
            return (null != candidateHuman && candidateHuman.contains((GroupResource) resource));
        } else if (Resource.MACHINE_TYPE.equals(resource.getType())) {
            // 是否需要设备资源 || 是否属于候选资源
            return (null != candidateLine && candidateLine.contains((MachineResource) resource));
        }
        return true;
    }

}
