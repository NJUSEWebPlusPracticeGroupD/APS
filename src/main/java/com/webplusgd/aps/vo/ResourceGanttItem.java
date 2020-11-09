package com.webplusgd.aps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceGanttItem {
    @Schema(description = "资源名", example = "line1")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(example = "2018-10-01 08:00", description = "时间段开始时间")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(example = "2018-10-01 10:00", description = "时间段结束时间")
    private Date toDate;

    @Schema(example = "418477", description = "订单id")
    private String task;
    @Schema(example = "true", description = "订单是否延期")
    private boolean delay;

}
