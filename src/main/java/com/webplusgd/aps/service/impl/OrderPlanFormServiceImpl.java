package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.service.OrderPlanFormService;
import com.webplusgd.aps.vo.OrderPlanItem;
import com.webplusgd.aps.vo.ResourceProduceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class OrderPlanFormServiceImpl implements OrderPlanFormService {

    public ArrayList<OrderPlanItem> orderPlanItems;
    public List<ScheduledTask> plan;

    @Override
    public ResponseVO<ArrayList<OrderPlanItem>> getOrderPlanForm() {

        OptaPlanner optaPlanner = new OptaPlanner();
        //todo: 获取plan

        if(plan==null||plan.size()==0){
            return ResponseVO.buildFailure("还未进行排程");
        }

        ArrayList<OrderPlanItem> res = new ArrayList<>();

        Map<Integer, ArrayList<ScheduledTask>> orderIdAndTime = new HashMap<>();

        //分每个订单的scheduledTask
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


        for(Integer orderId : orderIdAndTime.keySet()){

            //拿到每个订单的scheduledTask
            ArrayList<ScheduledTask> scheduledTasks = orderIdAndTime.get(orderId);
            //对scheduledTask根据timeslot排序（升序）

            //获取此订单生产的所有timeSlot并对timeSlot做去重
            List<Timeslot> curTimeSlots = new ArrayList<>();
            for(ScheduledTask st:scheduledTasks){
                boolean has = false;
                for(int i=0;i<curTimeSlots.size();i++){
                    if(timeSlotEqual(curTimeSlots.get(i),st.getTimeslot())){
                        has = true;
                        break;
                    }
                }
                if(!has){
                    curTimeSlots.add(st.getTimeslot());
                }
            }

            //对timeSlot排序
            curTimeSlots.sort(new Comparator<Timeslot>() {
                @Override
                public int compare(Timeslot o1, Timeslot o2) {
                    if(o1.getStartDateTime().isBefore(o2.getStartDateTime())){
                        return -1;
                    }
                    else if(o1.getStartDateTime().isAfter(o2.getStartDateTime())){
                        return 1;
                    }
                    else{
                        return 0;
                    }

                }
            });

            //---------------时间段合并，用于拆分子订单-----------------
            ArrayList<Timeslot> mergedTimeSlots = new ArrayList<>();


            for(int i=0;i<curTimeSlots.size();i++){
                if(mergedTimeSlots.size()==0){
                    Timeslot copy = new Timeslot();
                    copy.setStartDateTime(curTimeSlots.get(i).getStartDateTime());
                    copy.setEndDateTime(curTimeSlots.get(i).getEndDateTime());
                    mergedTimeSlots.add(copy);
                }
                else{

                    Timeslot curTimeSlot = curTimeSlots.get(i);
                    Timeslot curLast = mergedTimeSlots.get(mergedTimeSlots.size()-1);
                    if(curTimeSlot.getStartDateTime().isEqual(curLast.getEndDateTime())){
                        curLast.setEndDateTime(curTimeSlot.getEndDateTime());
                    }
                    else{
                        Timeslot copy = new Timeslot();
                        copy.setStartDateTime(curTimeSlot.getStartDateTime());
                        copy.setEndDateTime(curTimeSlot.getEndDateTime());
                        mergedTimeSlots.add(copy);
                    }
                }

            }

            if(mergedTimeSlots.size()==0){
                System.out.println("排程结果出错，有订单未参与排程");
                return ResponseVO.buildFailure("排程结果出错，有订单未参与排程");
            }

            //表格项
            OrderPlanItem orderPlanItem = new OrderPlanItem();
            //--------初始化订单项----------

            if(mergedTimeSlots.size()==1){
                //-----没有子订单----------
                orderPlanItem.setOrderNumber(orderId+"");
                orderPlanItem.setStartTime(mergedTimeSlots.get(0).getStartDateTime());
                orderPlanItem.setEndTime(mergedTimeSlots.get(0).getEndDateTime());
                orderPlanItem.setSplit(false);
                orderPlanItem.setTurnToOrderProductionTable(true);
                orderPlanItem.setChildren(null);
            }
            else{
                //------有子订单---------
                orderPlanItem.setOrderNumber(orderId+"");
                orderPlanItem.setTurnToOrderProductionTable(false);
                orderPlanItem.setSplit(true);
                orderPlanItem.setStartTime(mergedTimeSlots.get(0).getStartDateTime());
                orderPlanItem.setStartTime(mergedTimeSlots.get(mergedTimeSlots.size()-1).getEndDateTime());
                List<OrderPlanItem> children = new ArrayList<>();
                orderPlanItem.setChildren(children);
                int childIndex = 1;
                for(Timeslot mergedTime : mergedTimeSlots){
                    OrderPlanItem child = new OrderPlanItem();
                    child.setSplit(false);
                    child.setChildren(null);
                    child.setTurnToOrderProductionTable(true);
                    child.setOrderNumber((orderId+"")+"-"+(childIndex+""));
                    childIndex++;
                    child.setStartTime(mergedTime.getStartDateTime());
                    child.setEndTime(mergedTime.getEndDateTime());
                    children.add(child);
                }
            }

            //-------订单项初始化完毕---------
            res.add(orderPlanItem);

        }

        orderPlanItems = res;

        return ResponseVO.buildSuccess(res);
    }

    private boolean timeSlotEqual(Timeslot t1,Timeslot t2){
        return (t1.getStartDateTime().isEqual(t2.getStartDateTime())&&t1.getEndDateTime().isEqual(t2.getEndDateTime()));
    }
}
