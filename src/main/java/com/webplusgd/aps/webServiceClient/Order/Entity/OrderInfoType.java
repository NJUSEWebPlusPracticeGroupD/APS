
package com.webplusgd.aps.webServiceClient.Order.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>orderInfoType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="orderInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderId" type="{http://www.oldCompany.com/order}orderIdType"/>
 *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numOfOrder" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="termOfDelivery" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderInfoType",namespace = "http://www.oldCompany.com/order",propOrder = {
    "orderId",
    "item",
    "numOfOrder",
    "termOfDelivery",
    "comment",
    "length"
})
public class OrderInfoType {

    @XmlElement(required = true,namespace = "")
    protected String orderId;
    @XmlElement(required = true,namespace = "")
    protected String item;
    @XmlElement(required = true,namespace = "")
    protected int numOfOrder;
    @XmlElement(required = true,namespace = "")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar termOfDelivery;
    @XmlElement(required = true, defaultValue = "",namespace = "")
    protected String comment;
    @XmlElement(required = true, defaultValue = "",namespace = "")
    protected String length;

    /**
     * 获取orderId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * 获取item属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItem() {
        return item;
    }

    /**
     * 设置item属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItem(String value) {
        this.item = value;
    }

    /**
     * 获取numOfOrder属性的值。
     * 
     */
    public int getNumOfOrder() {
        return numOfOrder;
    }

    /**
     * 设置numOfOrder属性的值。
     * 
     */
    public void setNumOfOrder(int value) {
        this.numOfOrder = value;
    }

    /**
     * 获取termOfDelivery属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTermOfDelivery() {
        return termOfDelivery;
    }

    /**
     * 设置termOfDelivery属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTermOfDelivery(XMLGregorianCalendar value) {
        this.termOfDelivery = value;
    }

    /**
     * 获取comment属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置comment属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * 获取length属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLength() {
        return length;
    }

    /**
     * 设置length属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLength(String value) {
        this.length = value;
    }

}
