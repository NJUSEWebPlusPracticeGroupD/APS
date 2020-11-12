package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public interface ResourceGanttChartService {
    ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(Date date);
}
