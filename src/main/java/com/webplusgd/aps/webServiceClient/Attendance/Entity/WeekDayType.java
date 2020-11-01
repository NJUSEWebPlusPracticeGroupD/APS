
package com.webplusgd.aps.webServiceClient.Attendance.Entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>WeekDayType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="WeekDayType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="星期一"/>
 *     &lt;enumeration value="星期二"/>
 *     &lt;enumeration value="星期三"/>
 *     &lt;enumeration value="星期四"/>
 *     &lt;enumeration value="星期五"/>
 *     &lt;enumeration value="星期六"/>
 *     &lt;enumeration value="星期日"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WeekDayType", namespace = "http://www.oldCompany.com/attendance")
@XmlEnum
public enum WeekDayType {

    星期一,
    星期二,
    星期三,
    星期四,
    星期五,
    星期六,
    星期日;

    public String value() {
        return name();
    }

    public static WeekDayType fromValue(String v) {
        return valueOf(v);
    }

}
