
package com.webplusgd.aps.webServiceClient.Attendance.Entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ShiftKindType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ShiftKindType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="早班"/>
 *     &lt;enumeration value="晚班"/>
 *     &lt;enumeration value="全天"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ShiftKindType", namespace = "http://www.oldCompany.com/attendance")
@XmlEnum
public enum ShiftKindType {

    早班,
    晚班,
    全天;

    public String value() {
        return name();
    }

    public static ShiftKindType fromValue(String v) {
        return valueOf(v);
    }

}
