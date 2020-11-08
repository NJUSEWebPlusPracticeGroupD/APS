
package com.webplusgd.aps.webServiceClient.BOM.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>resourceInfoType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="resourceInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resourceMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resourceType" type="{http://www.oldCompany.com/bom}ResourceType"/>
 *         &lt;element name="leastNumOfResource" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="standardCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timeOfChangeLine" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceInfoType", namespace = "http://www.oldCompany.com/bom", propOrder = {
    "resourceName",
    "resourceMode",
    "resourceType",
    "leastNumOfResource",
    "standardCapacity",
    "priority",
    "timeOfChangeLine"
})
public class ResourceInfoType {

    @XmlElement(required = true)
    protected String resourceName;
    @XmlElement(required = true)
    protected String resourceMode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ResourceType resourceType;
    protected int leastNumOfResource;
    protected int standardCapacity;
    protected int priority;
    protected double timeOfChangeLine;

    /**
     * 获取resourceName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * 设置resourceName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceName(String value) {
        this.resourceName = value;
    }

    /**
     * 获取resourceMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceMode() {
        return resourceMode;
    }

    /**
     * 设置resourceMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceMode(String value) {
        this.resourceMode = value;
    }

    /**
     * 获取resourceType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * 设置resourceType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setResourceType(ResourceType value) {
        this.resourceType = value;
    }

    /**
     * 获取leastNumOfResource属性的值。
     * 
     */
    public int getLeastNumOfResource() {
        return leastNumOfResource;
    }

    /**
     * 设置leastNumOfResource属性的值。
     * 
     */
    public void setLeastNumOfResource(int value) {
        this.leastNumOfResource = value;
    }

    /**
     * 获取standardCapacity属性的值。
     * 
     */
    public int getStandardCapacity() {
        return standardCapacity;
    }

    /**
     * 设置standardCapacity属性的值。
     * 
     */
    public void setStandardCapacity(int value) {
        this.standardCapacity = value;
    }

    /**
     * 获取priority属性的值。
     * 
     */
    public int getPriority() {
        return priority;
    }

    /**
     * 设置priority属性的值。
     * 
     */
    public void setPriority(int value) {
        this.priority = value;
    }

    /**
     * 获取timeOfChangeLine属性的值。
     * 
     */
    public double getTimeOfChangeLine() {
        return timeOfChangeLine;
    }

    /**
     * 设置timeOfChangeLine属性的值。
     * 
     */
    public void setTimeOfChangeLine(double value) {
        this.timeOfChangeLine = value;
    }

}
