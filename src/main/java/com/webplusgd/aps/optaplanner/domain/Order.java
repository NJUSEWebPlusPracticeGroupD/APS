package com.webplusgd.aps.optaplanner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Rollingegg
 * @create_time 11/4/2020 9:13 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int orderId;
    private Product product;
    private int orderNum;
    private LocalDateTime termOfDeliver;
    /**
     * 订单完成的时间
     */
    private LocalDateTime finishTime;
}
