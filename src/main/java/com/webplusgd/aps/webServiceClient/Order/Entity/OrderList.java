
package com.webplusgd.aps.webServiceClient.Order.Entity;



import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="orderInfo" type="{http://www.oldCompany.com/order}orderInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderList", propOrder = {
    "orderInfo","orderInfoTypes"
})
@XmlRootElement(name = "orderList")
public class OrderList {

    public void setOrderInfo(List<OrderInfoType> orderInfo) {
        this.orderInfo = orderInfo;
    }
    @XmlElement(namespace = "http://www.oldCompany.com/order",required = true)
    protected List<OrderInfoType> orderInfo;

    public OrderInfoType[] getOrderInfoTypes() {
        return orderInfoTypes;
    }

    public void setOrderInfoTypes(OrderInfoType[] orderInfoTypes) {
        this.orderInfoTypes = orderInfoTypes;
    }

    @XmlElement(namespace = "http://www.oldCompany.com/order",required = true)
    protected OrderInfoType[] orderInfoTypes;
    /**
     * Gets the value of the orderInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderInfoType }
     * 
     * 
     */
    public List<OrderInfoType> getOrderInfo() {
        if (orderInfo == null) {
            orderInfo = new ArrayList<OrderInfoType>();
        }
        return this.orderInfo;
    }

}
