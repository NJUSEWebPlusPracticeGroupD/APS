package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.ResourceProduceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
@Component
public interface OrderProduceRelationFormService {
    ResponseVO<ArrayList<ResourceProduceItem>> getOrderProduceRelationForm(Date date, Integer orderId);
}
