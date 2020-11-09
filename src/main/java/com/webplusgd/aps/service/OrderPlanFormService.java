package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.OrderPlanItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public interface OrderPlanFormService {

    ResponseVO<ArrayList<OrderPlanItem>> getOrderPlanForm();
}
