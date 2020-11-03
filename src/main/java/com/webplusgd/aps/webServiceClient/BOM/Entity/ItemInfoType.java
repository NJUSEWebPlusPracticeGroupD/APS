
package com.webplusgd.aps.webServiceClient.BOM.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ItemInfoType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ItemInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="itemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="property" type="{http://www.oldCompany.com/erp}ItemPropertyType"/>
 *         &lt;element name="measurement" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="color" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="otherSpecification" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prepareMode" type="{http://www.oldCompany.com/erp}PrepareModeType"/>
 *         &lt;element name="MinimumPacking" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemInfoType", propOrder = {
    "itemId",
    "description",
    "property",
    "measurement",
    "color",
    "length",
    "hard",
    "otherSpecification",
    "prepareMode",
    "minimumPacking"
})
public class ItemInfoType {

    @XmlElement(required = true)
    protected String itemId;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ItemPropertyType property;
    @XmlElement(required = true)
    protected String measurement;
    @XmlElement(required = true, defaultValue = "")
    protected String color;
    @XmlElement(required = true, defaultValue = "")
    protected String length;
    @XmlElement(required = true, defaultValue = "")
    protected String hard;
    @XmlElement(required = true, defaultValue = "")
    protected String otherSpecification;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected PrepareModeType prepareMode;
    @XmlElement(name = "MinimumPacking", defaultValue = "1")
    protected int minimumPacking;

    /**
     * 获取itemId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置itemId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemId(String value) {
        this.itemId = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取property属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ItemPropertyType }
     *     
     */
    public ItemPropertyType getProperty() {
        return property;
    }

    /**
     * 设置property属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ItemPropertyType }
     *     
     */
    public void setProperty(ItemPropertyType value) {
        this.property = value;
    }

    /**
     * 获取measurement属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurement() {
        return measurement;
    }

    /**
     * 设置measurement属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurement(String value) {
        this.measurement = value;
    }

    /**
     * 获取color属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置color属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * 获取length属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLength() {
        return length;
    }

    /**
     * 设置length属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLength(String value) {
        this.length = value;
    }

    /**
     * 获取hard属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHard() {
        return hard;
    }

    /**
     * 设置hard属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHard(String value) {
        this.hard = value;
    }

    /**
     * 获取otherSpecification属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherSpecification() {
        return otherSpecification;
    }

    /**
     * 设置otherSpecification属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherSpecification(String value) {
        this.otherSpecification = value;
    }

    /**
     * 获取prepareMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PrepareModeType }
     *     
     */
    public PrepareModeType getPrepareMode() {
        return prepareMode;
    }

    /**
     * 设置prepareMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PrepareModeType }
     *     
     */
    public void setPrepareMode(PrepareModeType value) {
        this.prepareMode = value;
    }

    /**
     * 获取minimumPacking属性的值。
     * 
     */
    public int getMinimumPacking() {
        return minimumPacking;
    }

    /**
     * 设置minimumPacking属性的值。
     * 
     */
    public void setMinimumPacking(int value) {
        this.minimumPacking = value;
    }

}
