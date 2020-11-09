package com.webplusgd.aps.api;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author toby
 */
@Tag(name = "Form", description = "展示表格API")
public interface FormApi {
    /**
     * 获取订单计划表
     *
     * @return
     */
    @Operation(summary = "获取订单计划表")
    @Log("获取订单计划表")
    ResponseVO<ArrayList<OrderPlanItem>> getOrderPlanForm();

    /**
     * 获取订单生产单关系表
     *
     * @param date 查看日期
     * @return
     */
    @Operation(summary = "获取订单生产单关系表")
    @Log("获取订单生产单关系表")
    ResponseVO<ArrayList<OrderProduceItem>> getOrderProduceRelationForm(
            @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "当前查看日期", example = "2018-09-10")
            @RequestParam Date date,
            @Parameter(description = "要查询的哪个订单的生产单", example = "2018-09-10")
            @RequestParam Integer orderId
            );

    /**
     * 获取生产单
     *
     * @param date 查看日期
     * @return
     */
    @Operation(summary = "获取生产单")
    @Log("获取生产单")
    ResponseVO<ArrayList<OrderProduceItem>> getProduceForm(
            @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "当前查看日期", example = "2018-09-10")
            @RequestParam Date date);

    /**
     * 获取生产单（订单）资源关系表
     *
     * @return
     */
    @Operation(summary = "获取生产单（订单）资源关系表")
    @Log("获取生产单（订单）资源关系表")
    ResponseVO<OrderResourceForm> getProduceResourceForm();
}
