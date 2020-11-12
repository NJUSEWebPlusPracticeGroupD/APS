package com.webplusgd.aps.api;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.exception.NoPlanException;
import com.webplusgd.aps.vo.OrderGanttChart;
import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResourceLoadChart;
import com.webplusgd.aps.vo.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Rollingegg
 */
@Tag(name = "Chart", description = "展示图表API")
public interface ChartApi {
    /**
     * 获取资源甘特图
     *
     * @param date 查看日期
     * @return
     */
    @Operation(summary = "获取资源甘特图")
    @Log("获取资源甘特图")
    ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(
            @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "当前查看日期", example = "2018-09-10")
            @RequestParam Date date);

    /**
     * 获取订单甘特图
     *
     * @param date 查看日期
     * @return
     */
    @Operation(summary = "获取订单甘特图", tags = {"Chart"})
    @Log("获取订单甘特图")
    ResponseVO<OrderGanttChart> getOrderGanttChart(
            @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "当前查看日期", example = "2018-09-10")
            @RequestParam Date date);

    /**
     * 获取资源负载图
     *
     * @param startDate 查看开始日期后的一周
     * @return
     */
    @Operation(summary = "获取资源负载图")
    @Log("获取资源负载图")
    ResponseVO<ResourceLoadChart> getResourceLoadChart(
            @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "当前查看日期", example = "2018-09-10")
            @RequestParam Date startDate) throws NoPlanException;

}
