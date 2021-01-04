package com.webplusgd.aps.optaplanner.optional;

import com.webplusgd.aps.optaplanner.domain.Order;
import org.aspectj.weaver.ast.Or;

import java.util.Comparator;

/**
 * @author Rollingegg
 * @create_time 1/4/2021 8:54 PM
 */
public class OrderStrengthComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getTermOfDeliver().compareTo(o2.getTermOfDeliver()) != 0) {
            // 优先完成DDL靠前的订单
            return o1.getTermOfDeliver().compareTo(o2.getTermOfDeliver());
        }
        if (o1.getOrderNum() > o2.getOrderNum()) {
            // 优先完成数量少的订单
            return 1;
        }
        if (o1.getOrderId() > o2.getOrderId()) {
            return 1;
        }
        return 0;
    }
}
