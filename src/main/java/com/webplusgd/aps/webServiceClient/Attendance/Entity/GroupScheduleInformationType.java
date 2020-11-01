
package com.webplusgd.aps.webServiceClient.Attendance.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>GroupScheduleInformationType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="GroupScheduleInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroupInformation" type="{http://www.oldCompany.com/erp}GroupInformationType"/>
 *         &lt;element name="Shift" type="{http://www.oldCompany.com/attendance}ShiftType"/>
 *         &lt;element name="DaysOfWeek" type="{http://www.oldCompany.com/attendance}DaysOfWeekType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupScheduleInformationType", namespace = "http://www.oldCompany.com/attendance", propOrder = {
    "groupInformation",
    "shift",
    "daysOfWeek"
})
public class GroupScheduleInformationType {

    @XmlElement(name = "GroupInformation", required = true)
    protected GroupInformationType groupInformation;
    @XmlElement(name = "Shift", required = true)
    protected ShiftType shift;
    @XmlElement(name = "DaysOfWeek", required = true)
    protected DaysOfWeekType daysOfWeek;

    /**
     * 获取groupInformation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link GroupInformationType }
     *     
     */
    public GroupInformationType getGroupInformation() {
        return groupInformation;
    }

    /**
     * 设置groupInformation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link GroupInformationType }
     *     
     */
    public void setGroupInformation(GroupInformationType value) {
        this.groupInformation = value;
    }

    /**
     * 获取shift属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ShiftType }
     *     
     */
    public ShiftType getShift() {
        return shift;
    }

    /**
     * 设置shift属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ShiftType }
     *     
     */
    public void setShift(ShiftType value) {
        this.shift = value;
    }

    /**
     * 获取daysOfWeek属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DaysOfWeekType }
     *     
     */
    public DaysOfWeekType getDaysOfWeek() {
        return daysOfWeek;
    }

    /**
     * 设置daysOfWeek属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DaysOfWeekType }
     *     
     */
    public void setDaysOfWeek(DaysOfWeekType value) {
        this.daysOfWeek = value;
    }

}
