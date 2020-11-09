
package com.webplusgd.aps.webServiceClient.Order.Service;

import javax.xml.ws.WebFault;

import com.webplusgd.aps.webServiceClient.Order.Entity.OrderDoesNotExistFaultType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "OrderDoesNotExistFault", targetNamespace = "http://www.oldCompany.com/fault")
public class OrderDoesNotExistFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private OrderDoesNotExistFaultType faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public OrderDoesNotExistFault(String message, OrderDoesNotExistFaultType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public OrderDoesNotExistFault(String message, OrderDoesNotExistFaultType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.oldcompany.fault.OrderDoesNotExistFaultType
     */
    public OrderDoesNotExistFaultType getFaultInfo() {
        return faultInfo;
    }

}
