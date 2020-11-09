package com.webplusgd.aps.controller;

import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResourceLoadChart;
import com.webplusgd.aps.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;


@RestController
@RequestMapping("/chart")
public class ChartController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/getResourceGanttChart", method = RequestMethod.GET)
    public ResponseVO getResourceGanttChart(@RequestParam Date date){
        return ResponseVO.buildSuccess(new ArrayList<ResourceGanttItem>());
    }

    @RequestMapping(value = "/getOrderGanttChart", method = RequestMethod.GET)
    public ResponseVO getOrderGanttChart(@RequestParam Date date){

        return ResponseVO.buildSuccess(new ArrayList<OrderGanttItem>());
    }

    @RequestMapping(value = "/getResourceLoadChart", method = RequestMethod.GET)
    public ResponseVO getResourceLoadChart(@RequestParam Date startDate){

        return ResponseVO.buildSuccess(new ResourceLoadChart());
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseVO testConnection(@RequestParam String testStr){
        System.out.println("backend success! " + testStr);
        return ResponseVO.buildSuccess("frontend success!" + testStr);
    }


}
