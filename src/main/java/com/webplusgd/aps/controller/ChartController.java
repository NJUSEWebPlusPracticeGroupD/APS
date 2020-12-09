package com.webplusgd.aps.controller;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.api.ChartApi;
import com.webplusgd.aps.exception.NoPlanException;
import com.webplusgd.aps.service.ResourceLoadService;
import com.webplusgd.aps.service.OrderGanttChartService;
import com.webplusgd.aps.service.ResourceGanttChartService;
import com.webplusgd.aps.vo.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/chart")
public class ChartController implements ChartApi {

    private final ResourceGanttChartService resourceGanttChartService;

    private final OrderGanttChartService orderGanttChartService;

    private final ResourceLoadService resourceLoadService;

    public ChartController(ResourceGanttChartService resourceGanttChartService, OrderGanttChartService orderGanttChartService, ResourceLoadService resourceLoadService) {
        this.resourceGanttChartService = resourceGanttChartService;
        this.orderGanttChartService = orderGanttChartService;
        this.resourceLoadService = resourceLoadService;
    }

    @Override
    @GetMapping("/getResourceGanttChart")
    @Log("获取资源甘特图")
    public ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(@RequestParam Date date) {
        return resourceGanttChartService.getResourceGanttChart(date);
    }

    @Override
    @GetMapping("/getOrderGanttChart")
    @Log("获取订单甘特图")
    public ResponseVO<OrderGanttChart> getOrderGanttChart(@RequestParam Date date) {
        return orderGanttChartService.getOrderGanttChart(date);
    }

    @Override
    @GetMapping("/getResourceLoadChart")
    @Log("获取资源负载图")
    public ResponseVO<ResourceLoadChart> getResourceLoadChart(@RequestParam Date startDate) throws NoPlanException {
        return ResponseVO.buildSuccess(resourceLoadService.getResourceLoadChart(startDate));
    }

}
