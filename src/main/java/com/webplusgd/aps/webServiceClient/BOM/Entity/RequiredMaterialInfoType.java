
package com.webplusgd.aps.webServiceClient.BOM.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>requiredMaterialInfoType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="requiredMaterialInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="materialId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="materialProperty" type="{http://www.oldCompany.com/erp}ItemPropertyType"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requiredMaterialInfoType", namespace = "http://www.oldCompany.com/bom", propOrder = {
    "materialId",
    "materialProperty",
    "number",
    "unit"
})
public class RequiredMaterialInfoType {

    @XmlElement(required = true)
    protected String materialId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ItemPropertyType materialProperty;
    protected double number;
    @XmlElement(required = true)
    protected String unit;

    /**
     * 获取materialId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialId() {
        return materialId;
    }

    /**
     * 设置materialId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialId(String value) {
        this.materialId = value;
    }

    /**
     * 获取materialProperty属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ItemPropertyType }
     *     
     */
    public ItemPropertyType getMaterialProperty() {
        return materialProperty;
    }

    /**
     * 设置materialProperty属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ItemPropertyType }
     *     
     */
    public void setMaterialProperty(ItemPropertyType value) {
        this.materialProperty = value;
    }

    /**
     * 获取number属性的值。
     * 
     */
    public double getNumber() {
        return number;
    }

    /**
     * 设置number属性的值。
     * 
     */
    public void setNumber(double value) {
        this.number = value;
    }

    /**
     * 获取unit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置unit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

}
