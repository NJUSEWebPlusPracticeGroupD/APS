package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.FCFSPlanner;
import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ProduceFormService;
import com.webplusgd.aps.vo.OrderPlanItem;
import com.webplusgd.aps.vo.ResourceProduceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
@Component
public class ProduceFormServiceImpl implements ProduceFormService {

    @Autowired
    OrderPlanFormServiceImpl orderPlanFormService;
    @Autowired
    OptaPlanner planner;

    public ArrayList<ResourceProduceItem> ProduceItems;
    public List<ScheduledTask> plan;

    @Override
    public ResponseVO<ArrayList<ResourceProduceItem>> getProduceForm(Date date) {

        //todo: 获取plan
        plan = planner.waitForPlan();

        if(plan==null||plan.size()==0){
            return ResponseVO.buildFailure("还未进行排程");
        }

        ResponseVO responseVO = orderPlanFormService.getOrderPlanForm();
        if(!responseVO.getSuccess()){
            return ResponseVO.buildFailure("还没有排程计划或排程出错");
        }

        ArrayList<OrderPlanItem> orderPlanItems = orderPlanFormService.orderPlanItems;


        ArrayList<ResourceProduceItem> res = new ArrayList<>();


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        Date endDate = calendar.getTime();
        LocalDateTime startTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = startTime.plusDays(1);

        LocalDateTime[] timeSegment = new LocalDateTime[25];
        for(int i=0;i<25;i++){
            timeSegment[i] = startTime.plusHours(i);
        }
        if(!timeSegment[24].isEqual(endTime)){
            System.out.println("timeSegment fault.");
            return null;
        }

        //---------获取每个资源的scheduledTask----------
        Map<String, ArrayList<ScheduledTask>> resourceIdAndTime = new HashMap<>();
        for(int i=0;i<plan.size();i++){
            ScheduledTask st = plan.get(i);
            if(st.getTimeslot().getStartDateTime().isAfter(endTime)||st.getTimeslot().getEndDateTime().isBefore(startTime)){continue;}

            if(!resourceIdAndTime.containsKey(st.getResource().getName())){
                ArrayList<ScheduledTask> scheduledTasks = new ArrayList<>();
                scheduledTasks.add(st);
                resourceIdAndTime.put(st.getResource().getName(),scheduledTasks);
            }
            else{
                resourceIdAndTime.get(st.getResource().getName()).add(st);
            }
        }

        //---------------按照timeSlot排序----------

        for(String resourceId: resourceIdAndTime.keySet()){
            ArrayList<ScheduledTask> scheduledTasks = resourceIdAndTime.get(resourceId);

            scheduledTasks.sort(new Comparator<ScheduledTask>() {
                @Override
                public int compare(ScheduledTask o1, ScheduledTask o2) {
                    if(o1.getTimeslot().getEndDateTime().isBefore(o2.getTimeslot().getEndDateTime())){
                        return -1;
                    }
                    else if(o1.getTimeslot().getEndDateTime().isAfter(o2.getTimeslot().getEndDateTime())){
                        return 1;
                    }
                    else{
                        return 0;
                    }

                }
            });

            //--------初始化表项----------
            ResourceProduceItem resourceProduceItem = new ResourceProduceItem();
            resourceProduceItem.setResource(resourceId);
            ArrayList<String> times = new ArrayList<>();
            resourceProduceItem.setOrderFor24Hours(times);
            for(int i=0;i<24;i++){
                times.add("");
            }

            for(ScheduledTask st:scheduledTasks){
                //------每个schedule对应的订单号--------
                LocalDateTime start = st.getTimeslot().getStartDateTime();
                LocalDateTime end = st.getTimeslot().getEndDateTime();

                String curOrderId = st.getOrder().getOrderId()+"";
                //-----------子订单搜索----------
                OrderPlanItem curOderPlanItem = null;
                for(OrderPlanItem orderPlanItem:orderPlanItems){
                    if(orderPlanItem.getOrderNumber().equals(curOrderId)){
                        curOderPlanItem = orderPlanItem;
                    }
                }
                if(curOderPlanItem==null){
                    System.out.println("排程出错，缺少订单项");
                    return ResponseVO.buildFailure("排程出错，缺少订单项");
                }

                if(curOderPlanItem.getChildren()!=null){
                    //------有子订单----------
                    for(OrderPlanItem child:curOderPlanItem.getChildren()){
                        LocalDateTime childStart = child.getStartTime();
                        LocalDateTime childEnd = child.getEndTime();
                        if((start.isEqual(childStart)||start.isAfter(childStart))&&
                                (end.isEqual(childEnd)||end.isBefore(childEnd))){
                            //-------属于此子订单时间段--------
                            curOrderId = child.getOrderNumber();
                            break;
                        }
                    }
                }

                int startIndex = 0;
                int endIndex = 24;
                for(int j=0;j<timeSegment.length;j++){
                    if(start.isEqual(timeSegment[j])){
                        startIndex = j;
                    }
                    if(end.isEqual(timeSegment[j])){
                        endIndex = j;
                    }
                }

                for(int j=startIndex;j<endIndex;j++){
                    times.set(j,curOrderId);
                }
            }


            res.add(resourceProduceItem);
        }

        ProduceItems = res;


        return ResponseVO.buildSuccess(res);
    }
}
