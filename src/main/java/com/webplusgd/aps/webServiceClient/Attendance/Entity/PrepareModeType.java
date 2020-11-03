
package com.webplusgd.aps.webServiceClient.Attendance.Entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PrepareModeType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="PrepareModeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="自制"/>
 *     &lt;enumeration value="外购"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PrepareModeType")
@XmlEnum
public enum PrepareModeType {

    自制,
    外购;

    public String value() {
        return name();
    }

    public static PrepareModeType fromValue(String v) {
        return valueOf(v);
    }

}
