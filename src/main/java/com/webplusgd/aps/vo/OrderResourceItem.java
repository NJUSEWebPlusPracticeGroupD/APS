package com.webplusgd.aps.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResourceItem {
    @Schema(example = "421093",description = "订单号")
    private String orderId;
    @Schema(example = "[\"line01\",\"21组-陈荷花（4）\",\"36组-谢春霞（4）\"]",description = "此订单在整个生产过程中所用的资源列表")
    private List<String> usedResources;
}
