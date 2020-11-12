package com.webplusgd.aps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceLoadItem {

    @Schema(example = "2018-07-13", description = "起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Schema(example = "line1", description = "资源名称")
    private String name;

    @Schema(example = "[30,20,40,50,80,20,10]", description = "起始日期在内的七天负载率")
    private List<Double> rates;

}
