package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.OrderPlanFormService;
import com.webplusgd.aps.vo.OrderPlanItem;
import com.webplusgd.aps.vo.ResponseVO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPlanFormServiceImpl implements OrderPlanFormService {

    public ArrayList<OrderPlanItem> orderPlanItems;
    public List<ScheduledTask> plan;

    @Override
    public ResponseVO<ArrayList<OrderPlanItem>> getOrderPlanForm() {
        OptaPlanner optaPlanner = new OptaPlanner();

        Map<Integer, ArrayList<ScheduledTask>> orderIdAndTime = new HashMap<>();
        for(int i=0;i<plan.size();i++){
            ScheduledTask st = plan.get(i);
            if(orderIdAndTime.containsKey(st.getOrder().getOrderId())){
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

        }


        return null;
    }
}
