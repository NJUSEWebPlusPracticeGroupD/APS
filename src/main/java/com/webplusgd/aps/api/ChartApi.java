package com.webplusgd.aps.api;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.vo.OrderGanttItem;
import com.webplusgd.aps.vo.ResourceGanttItem;
import com.webplusgd.aps.vo.ResourceLoadChart;
import com.webplusgd.aps.vo.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Log("获取资源甘特图")
    ResponseVO<ArrayList<ResourceGanttItem>> getResourceGanttChart(@RequestParam Date date);

    /**
     * 获取订单甘特图
     *
     * @param date 查看日期
     * @return
     */
    @Operation(summary = "获取订单甘特图", tags = {"Chart"})
    @Log("获取订单甘特图")
    ResponseVO<ArrayList<OrderGanttItem>> getOrderGanttChart(@RequestParam Date date);

    /**
     * 获取资源负载图
     *
     * @param startDate 查看开始日期后的一周
     * @return
     */
    @Log("获取资源负载图")
    ResponseVO<ResourceLoadChart> getResourceLoadChart(@Parameter(description = "当前查看日期", example = "2018/09/10") @RequestParam Date startDate);
}
