package com.webplusgd.aps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceLoadChart {
    @Schema(example = "50", description = "设备总负载率")
    private double totalEquipmentLoadRate;
    @Schema(example = "80", description = "人员总负载率")
    private double totalPersonnelLoadRate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(example = "2018-07-13", description = "起始日期")
    private LocalDate startDate;
    @Schema(description = "七天负载数据")
    private List<ResourceLoadItem> resourceLoadItems;
}
