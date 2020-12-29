package com.webServiceServer.Entity.OrderForm;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WsOrderPlanItem", propOrder = {
        "orderNumber","isSplit","startTime","endTime","children"
})
public class WsOrderPlanItem {
    @XmlElement(required = true,namespace = "http://www.aps.com/orderForm")
    private String orderNumber;

    @XmlElement(required = true,namespace = "http://www.aps.com/orderForm")
    private boolean isSplit;

    @XmlElement(required = true,namespace = "http://www.aps.com/orderForm")
    private LocalDateTime startTime;

    @XmlElement(required = true,namespace = "http://www.aps.com/orderForm")
    private LocalDateTime endTime;
    @XmlElement(required = true,namespace = "http://www.aps.com/orderForm")
    private List<WsOrderPlanItem> children;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public boolean isSplit() {
        return isSplit;
    }

    public void setSplit(boolean split) {
        isSplit = split;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<WsOrderPlanItem> getChildren() {
        return children;
    }

    public void setChildren(List<WsOrderPlanItem> children) {
        this.children = children;
    }

    //----序列化字段名称覆写-----
    public boolean getIsSplit() {
        return isSplit;
    }
}
