package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.OrderProduceItem;
import com.webplusgd.aps.vo.ResponseVO;

import java.util.ArrayList;
import java.util.Date;

public interface ProduceFormService
{
    ResponseVO<ArrayList<OrderProduceItem>> getProduceForm(Date date);
}
