package com.webplusgd.aps.controller;

import com.webplusgd.aps.model.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/chart")
public class ChartController {

    @RequestMapping(value = "/getResourceGanttChart", method = RequestMethod.GET)
    public ResponseVO getResourceGanttChart(@RequestParam Date date){
        return null;
    }

    @RequestMapping(value = "/getOrderGanttChart", method = RequestMethod.GET)
    public ResponseVO getOrderGanttChart(@RequestParam Date date){
        return null;
    }

    @RequestMapping(value = "/getResourceLoadChart", method = RequestMethod.GET)
    public ResponseVO getResourceLoadChart(@RequestParam Date startDate){
        return null;
    }


}
