package com.webplusgd.aps.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResourceForm {
    @Schema(description = "每个订单所对应的资源列表的列表")
    private List<OrderResourceItem> resourceItems;
    @Schema(example = "[\"line01\",\"line02\",\"line03\",\"line04\",\"line05\",\"5组-童小玲 （5）\",\"6组-李倩（4）\",\"21组-陈荷花（4）\", \"36组-谢春霞（4）\"]",description = "所有资源名的列表，即订单资源表的每一行的表头")
    private List<String> allResourcesLabels;
}
