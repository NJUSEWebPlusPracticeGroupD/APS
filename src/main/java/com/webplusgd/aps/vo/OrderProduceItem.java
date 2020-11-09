package com.webplusgd.aps.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduceItem {
    @Schema(example = "line01",description = "资源名")
    private String resource;
    @Schema(example = "[4120934,,,,,,1303533,,4120934,4120934,,,,,,,,4120934,4120934,,,,,]",description = "此资源在给定date对应的24小时分别生产什么订单")
    private List<String> orderFor24Hours;
}
