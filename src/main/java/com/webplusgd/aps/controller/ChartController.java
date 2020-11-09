package com.webplusgd.aps.controller;

import com.webplusgd.aps.api.ChartApi;
import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResourceLoadChart;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/chart")
public class ChartController implements ChartApi {

    @Override
    @GetMapping("/getResourceGanttChart")
    public ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(@RequestParam Date date) {

        return ResponseVO.buildSuccess(new ArrayList<ResourceGanttItem>());
    }

    @Override
    @GetMapping("/getOrderGanttChart")
    public ResponseVO<ArrayList<OrderGanttItem>> getOrderGanttChart(@RequestParam Date date) {

        return ResponseVO.buildSuccess(new ArrayList<OrderGanttItem>());
    }

    @Override
    @GetMapping("/getResourceLoadChart")
    public ResponseVO<ResourceLoadChart> getResourceLoadChart(@RequestParam Date startDate) {

        return ResponseVO.buildSuccess(new ResourceLoadChart());
    }


}
