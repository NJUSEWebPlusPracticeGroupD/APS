package com.webplusgd.aps.service.impl;

import com.webplusgd.aps.service.OrderProduceRelationFormService;
import com.webplusgd.aps.vo.ResourceProduceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
@Component
public class OrderProduceRelationFormServiceImpl implements OrderProduceRelationFormService {

    @Autowired
    ProduceFormServiceImpl produceFormService;

    @Override
    public ResponseVO<ArrayList<ResourceProduceItem>> getOrderProduceRelationForm(Date date, Integer orderId) {
        produceFormService.getProduceForm(date);
        ArrayList<ResourceProduceItem> allProduces = produceFormService.ProduceItems;
        ArrayList<ResourceProduceItem> res = new ArrayList<>();
        for(ResourceProduceItem rpi:allProduces){
            if(rpi.getOrderFor24Hours().contains(orderId+"")){
                ResourceProduceItem newRpi = new ResourceProduceItem();
                newRpi.setResource(rpi.getResource());
                ArrayList<String> newTime = new ArrayList<>(rpi.getOrderFor24Hours());
                for(int i=0;i<newTime.size();i++){
                    if(!newTime.get(i).equals(orderId+"")){
                        newTime.set(i,"");
                    }
                }
                newRpi.setOrderFor24Hours(newTime);
                res.add(newRpi);
            }
        }


        return ResponseVO.buildSuccess(res);
    }
}
