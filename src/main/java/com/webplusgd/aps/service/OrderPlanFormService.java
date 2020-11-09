package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.OrderPlanItem;
import com.webplusgd.aps.vo.ResponseVO;

import java.util.ArrayList;

public interface OrderPlanFormService {

    ResponseVO<ArrayList<OrderPlanItem>> getOrderPlanForm();
}
