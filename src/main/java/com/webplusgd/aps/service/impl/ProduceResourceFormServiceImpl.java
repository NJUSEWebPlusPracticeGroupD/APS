package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.OptaPlanner;
import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ProduceResourceFormService;
import com.webplusgd.aps.vo.OrderResourceForm;
import com.webplusgd.aps.vo.OrderResourceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProduceResourceFormServiceImpl implements ProduceResourceFormService {

    public List<ScheduledTask> plan;


    @Override
    public ResponseVO<OrderResourceForm> getProduceResourceForm() {
        OptaPlanner optaPlanner = new OptaPlanner();
        //todo: 获取plan

        if(plan==null||plan.size()==0){
            return ResponseVO.buildFailure("还未进行排程");
        }

        OrderResourceForm res = new OrderResourceForm();
        ArrayList<OrderResourceItem> resourceItems = new ArrayList<>();
        res.setResourceItems(resourceItems);
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
            Set<String> curResources = new HashSet<>();
            OrderResourceItem ori = new OrderResourceItem();
            ori.setOrderId(key);
            for(ScheduledTask st:scheduledTasks){
                curResources.add(st.getResource().getName());
                allResources.add(st.getResource().getName());
            }
            ori.setUsedResources(new ArrayList<String>(curResources));
            resourceItems.add(ori);
        }
        res.setAllResourcesLabels(new ArrayList<String>(allResources));

        return ResponseVO.buildSuccess(res);
    }
}
