package com.webplusgd.aps.controller;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.api.FormApi;
import com.webplusgd.aps.service.OrderPlanFormService;
import com.webplusgd.aps.service.OrderProduceRelationFormService;
import com.webplusgd.aps.service.ProduceFormService;
import com.webplusgd.aps.service.ProduceResourceFormService;
import com.webplusgd.aps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/form")
public class FormController implements FormApi {

    @Autowired
    OrderPlanFormService orderPlanFormService;

    @Autowired
    ProduceFormService produceFormService;

    @Autowired
    ProduceResourceFormService produceResourceFormService;

    @Autowired
    OrderProduceRelationFormService orderProduceRelationFormService;

    @Override
    @GetMapping("/getOrderPlanForm")
    @Log("获取订单计划表")
    public ResponseVO<ArrayList<OrderPlanItem>> getOrderPlanForm() {

        return orderPlanFormService.getOrderPlanForm();
    }

    @Override
    @GetMapping("/getOrderProduceRelationForm")
    @Log("获取订单生产单关系表")
    public ResponseVO<ArrayList<ResourceProduceItem>> getOrderProduceRelationForm(@RequestParam Date date, @RequestParam String orderId) {
        return orderProduceRelationFormService.getOrderProduceRelationForm(date,orderId);
    }

    @Override
    @GetMapping("/getProduceForm")
    @Log("获取生产单")
    public ResponseVO<ArrayList<ResourceProduceItem>> getProduceForm(@RequestParam Date date) {


        return produceFormService.getProduceForm(date);
    }

    @Override
    @GetMapping("/getProduceResourceForm")
    @Log("获取生产单（订单）资源关系表")
    public ResponseVO<OrderResourceForm> getProduceResourceForm() {
        return produceResourceFormService.getProduceResourceForm();
    }
}
