
package com.webplusgd.aps.webServiceClient.Attendance.Entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ItemPropertyType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ItemPropertyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="成品"/>
 *     &lt;enumeration value="半成品"/>
 *     &lt;enumeration value="原材料"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ItemPropertyType")
@XmlEnum
public enum ItemPropertyType {

    成品,
    半成品,
    原材料;

    public String value() {
        return name();
    }

    public static ItemPropertyType fromValue(String v) {
        return valueOf(v);
    }

}
