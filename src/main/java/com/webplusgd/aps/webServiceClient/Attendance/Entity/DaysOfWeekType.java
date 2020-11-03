
package com.webplusgd.aps.webServiceClient.Attendance.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DaysOfWeekType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DaysOfWeekType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startDay" type="{http://www.oldCompany.com/attendance}WeekDayType"/>
 *         &lt;element name="endDay" type="{http://www.oldCompany.com/attendance}WeekDayType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DaysOfWeekType", namespace = "http://www.oldCompany.com/attendance", propOrder = {
    "startDay",
    "endDay"
})
public class DaysOfWeekType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected WeekDayType startDay;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected WeekDayType endDay;

    /**
     * 获取startDay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WeekDayType }
     *     
     */
    public WeekDayType getStartDay() {
        return startDay;
    }

    /**
     * 设置startDay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WeekDayType }
     *     
     */
    public void setStartDay(WeekDayType value) {
        this.startDay = value;
    }

    /**
     * 获取endDay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WeekDayType }
     *     
     */
    public WeekDayType getEndDay() {
        return endDay;
    }

    /**
     * 设置endDay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WeekDayType }
     *     
     */
    public void setEndDay(WeekDayType value) {
        this.endDay = value;
    }

}
