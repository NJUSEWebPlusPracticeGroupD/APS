package com.webplusgd.aps.optaplanner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private long orderNum;
    private LocalDateTime termOfDeliver;
    /**
     * 订单开始排程的时间(暂时没有设置，需要业务类来计算)
     */
    private LocalDateTime startTime;
    /**
     * 订单完成的时间
     */
    private LocalDateTime finishTime;

    public static Order defaultOrder;

    public static Order getDefaultOrder() {
        if (defaultOrder == null) {
            defaultOrder = new Order();
            defaultOrder.orderId = 0;
        }
        return defaultOrder;
    }

    public int getRequiredPeople(){
        return product.getMinimumStaff();
    }

    public boolean equals(Order other){
        return orderId==other.orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                orderNum == order.orderNum &&
                product.equals(order.product) &&
                termOfDeliver.equals(order.termOfDeliver) &&
                Objects.equals(startTime, order.startTime) &&
                Objects.equals(finishTime, order.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, product, orderNum, termOfDeliver, startTime, finishTime);
    }
}
