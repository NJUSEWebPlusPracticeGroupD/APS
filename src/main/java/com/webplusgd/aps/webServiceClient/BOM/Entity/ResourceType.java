
package com.webplusgd.aps.webServiceClient.BOM.Entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ResourceType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ResourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="主资源"/>
 *     &lt;enumeration value="副资源"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ResourceType", namespace = "http://www.oldCompany.com/bom")
@XmlEnum
public enum ResourceType {

    主资源,
    副资源;

    public String value() {
        return name();
    }

    public static ResourceType fromValue(String v) {
        return valueOf(v);
    }

}
