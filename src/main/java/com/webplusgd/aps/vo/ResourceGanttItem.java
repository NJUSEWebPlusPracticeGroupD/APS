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
public class ResourceGanttItem implements Comparable<ResourceGanttItem> {
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

    @Override
    public int compareTo(ResourceGanttItem resourceGanttItem) {
        if (this.getName().startsWith("line") && resourceGanttItem.getName().startsWith("line")) {
            // 为方便排序将 line 序列转为 int 类型
            return Integer.parseInt(this.getName().substring(4)) - Integer.parseInt(resourceGanttItem.getName().substring(4));
        } else if (this.getName().startsWith("line")) {
            return -1;
        } else if (resourceGanttItem.getName().startsWith("line")) {
            return 1;
        } else {
            return -1;
        }
    }

}
