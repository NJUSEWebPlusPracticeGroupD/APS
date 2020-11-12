package com.webplusgd.aps.service;

import com.webplusgd.aps.vo.OrderGanttChart;
import com.webplusgd.aps.vo.ResponseVO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface OrderGanttChartService {
    ResponseVO<OrderGanttChart> getOrderGanttChart(Date date);
}
