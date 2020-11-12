package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.FCFSPlanner;
import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ProduceResourceFormService;
import com.webplusgd.aps.vo.OrderPlanItem;
import com.webplusgd.aps.vo.OrderResourceForm;
import com.webplusgd.aps.vo.OrderResourceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ProduceResourceFormServiceImpl implements ProduceResourceFormService {


    @Autowired
    OrderPlanFormServiceImpl orderPlanFormService;

    @Autowired
    FCFSPlanner fcfsPlanner;

    public List<ScheduledTask> plan;


    @Override
    public ResponseVO<OrderResourceForm> getProduceResourceForm() {

        //todo: 获取plan
        plan = fcfsPlanner.waitForPlan();

        if(plan==null||plan.size()==0){
            return ResponseVO.buildFailure("还未进行排程");
        }

        ResponseVO responseVO = orderPlanFormService.getOrderPlanForm();
        if(!responseVO.getSuccess()){
            return ResponseVO.buildFailure("还没有排程计划或排程出错");
        }

        ArrayList<OrderPlanItem> orderPlanItems = orderPlanFormService.orderPlanItems;


        OrderResourceForm res = new OrderResourceForm();
        ArrayList<OrderResourceItem> resourceItems = new ArrayList<>();
        Set<String> allResources = new HashSet<>();


        Map<Integer, ArrayList<ScheduledTask>> orderIdAndTime = new HashMap<>();
        for(int i=0;i<plan.size();i++){
            ScheduledTask st = plan.get(i);
            if(!orderIdAndTime.containsKey(st.getOrder().getOrderId())){
                ArrayList<ScheduledTask> scheduledTasks = new ArrayList<>();
                scheduledTasks.add(st);
                orderIdAndTime.put(st.getOrder().getOrderId(),scheduledTasks);
            }
            else{
                orderIdAndTime.get(st.getOrder().getOrderId()).add(st);
            }
        }

        for(Integer key : orderIdAndTime.keySet()){
            ArrayList<ScheduledTask> scheduledTasks = orderIdAndTime.get(key);

//            OrderResourceItem ori = new OrderResourceItem();

            //---------处理子订单逻辑--------------
            String curOrderId = key+"";
            OrderPlanItem curPlan = null;
            for(OrderPlanItem orderPlanItem:orderPlanItems){
                if(orderPlanItem.getOrderNumber().equals(curOrderId)){
                    curPlan = orderPlanItem;
                    break;
                }
            }
            if(curPlan==null){
                System.out.println("排程结果出错,订单未找到");
                return ResponseVO.buildFailure("排程结果出错，订单未找到");
            }
            if(curPlan.getChildren()!=null){
                //-----有子订单-------
                if(curPlan.getChildren().size()<=1){
                    System.out.println("订单计划分割子订单出错，有长度小于等于1的children");
                    return ResponseVO.buildFailure("订单计划分割子订单出错，有长度小于等于1的children");
                }

                List<OrderPlanItem> children = curPlan.getChildren();
                for(OrderPlanItem child:children){
                    String childId = child.getOrderNumber();
                    //-------初始化表项--------
                    Set<String> curResources = new HashSet<>();
                    OrderResourceItem ori = new OrderResourceItem();
                    ori.setOrderId(childId);
                    for(ScheduledTask st:scheduledTasks){
                        //--------对每一个curOrderId的st遍历，找出在子订单时间内的--------
                        LocalDateTime start = st.getTimeslot().getStartDateTime();
                        LocalDateTime end = st.getTimeslot().getEndDateTime();
                        LocalDateTime childStart = child.getStartTime();
                        LocalDateTime childEnd = child.getEndTime();
                        if((start.isEqual(childStart)||start.isAfter(childStart))&&
                                (end.isEqual(childEnd)||end.isBefore(childEnd))){
                            //-------属于此子订单时间段--------
                            curResources.add(st.getResource().getName());
                            allResources.add(st.getResource().getName());
                        }
                    }
                    //-----------设置子订单使用的资源------------
                    ori.setUsedResources(new ArrayList<String>(curResources));
                    resourceItems.add(ori);
                }
            }
            else{
                OrderResourceItem ori = new OrderResourceItem();
                Set<String> curResources = new HashSet<>();
                ori.setOrderId(curOrderId);
                for(ScheduledTask st:scheduledTasks){
                    curResources.add(st.getResource().getName());
                    allResources.add(st.getResource().getName());
                }
                ori.setUsedResources(new ArrayList<String>(curResources));
                resourceItems.add(ori);
            }


        }
        res.setResourceItems(resourceItems);
        ArrayList<String> allResourcesArray = new ArrayList<>(allResources);
        ArrayList<String> sortedResourceArray = new ArrayList<>();

        for(String a:allResourcesArray){
            if(a.contains("line")){
                sortedResourceArray.add(a);
            }
        }

        for(String a:allResourcesArray){
            if(!a.contains("line")){
                sortedResourceArray.add(a);
            }
        }

        res.setAllResourcesLabels(sortedResourceArray);

        return ResponseVO.buildSuccess(res);
    }
}
