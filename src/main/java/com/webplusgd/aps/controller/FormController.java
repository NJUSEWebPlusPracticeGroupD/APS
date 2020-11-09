package com.webplusgd.aps.controller;

import com.webplusgd.aps.api.FormApi;
import com.webplusgd.aps.vo.OrderPlanItem;
import com.webplusgd.aps.vo.OrderProduceItem;
import com.webplusgd.aps.vo.OrderResourceItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/form")
public class FormController implements FormApi {

    @Override
    @GetMapping("/getOrderPlanForm")
    public ResponseVO<ArrayList<OrderPlanItem>> getOrderPlanForm() {
        return null;
    }

    @Override
    @GetMapping("/getOrderProduceRelationForm")
    public ResponseVO<ArrayList<OrderProduceItem>> getOrderProduceRelationForm(Date date) {
        return null;
    }

    @Override
    @GetMapping("/getProduceForm")
    public ResponseVO<ArrayList<OrderProduceItem>> getProduceForm(Date date) {
        return null;
    }

    @Override
    @GetMapping("/getProduceResourceForm")
    public ResponseVO<ArrayList<OrderResourceItem>> getProduceResourceForm() {
        return null;
    }
}
