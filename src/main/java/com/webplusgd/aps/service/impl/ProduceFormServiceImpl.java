package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.optaplanner.ScheduledTask;
import com.webplusgd.aps.service.ProduceFormService;
import com.webplusgd.aps.vo.ResourceProduceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
@Component
public class ProduceFormServiceImpl implements ProduceFormService {

    public ArrayList<ResourceProduceItem> ProduceItems;
    public List<ScheduledTask> plan;

    @Override
    public ResponseVO<ArrayList<ResourceProduceItem>> getProduceForm(Date date) {

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

        LocalDateTime[] timeSegment = new LocalDateTime[24];
        for(int i=0;i<24;i++){
            timeSegment[i] = startTime.plusHours(i);
        }
        if(!timeSegment[23].isEqual(endTime)){
            System.out.println("timeSegment fault.");
            return null;
        }

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

            ResourceProduceItem resourceProduceItem = new ResourceProduceItem();
            resourceProduceItem.setResource(resourceId);


            ArrayList<String> times = new ArrayList<>();
            for(int i=0;i<24;i++){
                times.add("");
            }

            for(ScheduledTask st:scheduledTasks){
                LocalDateTime start = st.getTimeslot().getStartDateTime();
                LocalDateTime end = st.getTimeslot().getEndDateTime();
                int startIndex = 0;
                int endIndex = 23;
                for(int j=0;j<timeSegment.length;j++){
                    if(start.isEqual(timeSegment[j])){
                        startIndex = j;
                    }
                    if(end.isEqual(timeSegment[j])){
                        endIndex = j;
                    }
                }
                for(int j=startIndex;j<endIndex;j++){
                    times.set(j,st.getOrder().getOrderId()+"");
                }
            }

            resourceProduceItem.setOrderFor24Hours(times);
            res.add(resourceProduceItem);
        }

        ProduceItems = res;


        return ResponseVO.buildSuccess(res);
    }
}
