
package com.webplusgd.aps.webServiceClient.Erp.Entity;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ItemDoesNotExistFault", targetNamespace = "http://www.oldCompany.com/fault")
public class ItemDoesNotExistFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ItemDoesNotExistFaultType faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public ItemDoesNotExistFault(String message, ItemDoesNotExistFaultType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public ItemDoesNotExistFault(String message, ItemDoesNotExistFaultType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: Erp.Entity.ItemDoesNotExistFaultType
     */
    public ItemDoesNotExistFaultType getFaultInfo() {
        return faultInfo;
    }

}
