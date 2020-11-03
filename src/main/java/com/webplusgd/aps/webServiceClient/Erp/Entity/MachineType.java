
package com.webplusgd.aps.webServiceClient.Erp.Entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>machineType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="machineType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="line"/>
 *     &lt;enumeration value="equipment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "machineType")
@XmlEnum
public enum MachineType {

    @XmlEnumValue("line")
    LINE("line"),
    @XmlEnumValue("equipment")
    EQUIPMENT("equipment");
    private final String value;

    MachineType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MachineType fromValue(String v) {
        for (MachineType c: MachineType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
