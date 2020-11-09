package com.webplusgd.aps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlanItem {
    @Schema(example = "418477",description = "订单号")
    private Integer orderNumber;
    @Schema(example = "true",description = "订单是否拆分")
    private boolean isSplit;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(example = "2018-07-13", description = "订单生产起始日期")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(example = "2018-07-13", description = "订单生产结束日期")
    private LocalDateTime endTime;
    @Schema(example = "true", description = "是否直接跳转到生产单（与订单是否拆分相同）")
    private boolean turnToOrderProductionTable;
    @Schema(description = "若订单拆分，children为子订单列表")
    private List<OrderPlanItem> children;
}
