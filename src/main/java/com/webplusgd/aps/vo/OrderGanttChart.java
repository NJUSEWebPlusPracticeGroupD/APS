package com.webplusgd.aps.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGanttChart {

    @Schema(example = "0.5", description = "按期交货率")
    private double onTimeDelivery;
    @Schema(description = "指定日期的订单数据")
    private ArrayList<OrderGanttItem> orderGanttItems;

}
