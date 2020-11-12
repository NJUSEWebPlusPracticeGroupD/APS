package com.webplusgd.aps.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGanttItem {
    @Schema(example = "418477",description = "订单号")
    private String orderId;
    @Schema(example = "60",description = "没有延期时的订单进度，单位百分比")
    private int progress;
    @Schema(example = "40",description = "延期后完成的进度")
    private int progressDelay;
}
