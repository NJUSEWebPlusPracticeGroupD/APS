
package com.webplusgd.aps.webServiceClient.Attendance.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ShiftType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ShiftType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shiftKind" type="{http://www.oldCompany.com/attendance}ShiftKindType"/>
 *         &lt;element name="shiftTime" type="{http://www.oldCompany.com/attendance}ShiftTimeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShiftType", namespace = "http://www.oldCompany.com/attendance", propOrder = {
    "shiftKind",
    "shiftTime"
})
public class ShiftType {

    @XmlElement(required = true,namespace = "")
    @XmlSchemaType(name = "string")
    protected ShiftKindType shiftKind;
    @XmlElement(required = true,namespace = "")
    protected ShiftTimeType shiftTime;

    /**
     * 获取shiftKind属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ShiftKindType }
     *     
     */
    public ShiftKindType getShiftKind() {
        return shiftKind;
    }

    /**
     * 设置shiftKind属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ShiftKindType }
     *     
     */
    public void setShiftKind(ShiftKindType value) {
        this.shiftKind = value;
    }

    /**
     * 获取shiftTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ShiftTimeType }
     *     
     */
    public ShiftTimeType getShiftTime() {
        return shiftTime;
    }

    /**
     * 设置shiftTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ShiftTimeType }
     *     
     */
    public void setShiftTime(ShiftTimeType value) {
        this.shiftTime = value;
    }

}
