package com.webplusgd.aps.controller;

import com.webplusgd.aps.api.FormApi;
import com.webplusgd.aps.vo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseVO<ArrayList<ResourceProduceItem>> getOrderProduceRelationForm(@RequestParam Date date, @RequestParam Integer orderId) {
        return null;
    }

    @Override
    @GetMapping("/getProduceForm")
    public ResponseVO<ArrayList<ResourceProduceItem>> getProduceForm(@RequestParam Date date) {
        return null;
    }

    @Override
    @GetMapping("/getProduceResourceForm")
    public ResponseVO<OrderResourceForm> getProduceResourceForm() {
        return null;
    }
}
