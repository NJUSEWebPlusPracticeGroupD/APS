package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResponseVO;

import java.util.ArrayList;
import java.util.Date;

public interface ResourceGanttChartService {
    ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(Date date);
}
