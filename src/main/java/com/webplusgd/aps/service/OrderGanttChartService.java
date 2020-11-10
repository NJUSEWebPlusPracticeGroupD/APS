package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResponseVO;

import java.util.ArrayList;
import java.util.Date;

public interface OrderGanttChartService {
    ResponseVO<ArrayList<OrderGanttItem>> getOrderGanttChart(Date date);
}
