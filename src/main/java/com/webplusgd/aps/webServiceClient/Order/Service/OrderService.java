
package com.webplusgd.aps.webServiceClient.Order.Service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OrderService", targetNamespace = "http://www.oldSystem.com/wsdl", wsdlLocation = "file:/D:/oceania-iter2/webclientTest/xml/wsdl/OrderManage.wsdl")
public class OrderService
    extends Service
{

    private final static URL ORDERSERVICE_WSDL_LOCATION;
    private final static WebServiceException ORDERSERVICE_EXCEPTION;
    private final static QName ORDERSERVICE_QNAME = new QName("http://www.oldSystem.com/wsdl", "OrderPortImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8088/ws/order" + "?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ORDERSERVICE_WSDL_LOCATION = url;
        ORDERSERVICE_EXCEPTION = e;
    }

    public OrderService() {
        super(__getWsdlLocation(), ORDERSERVICE_QNAME);
    }

    public OrderService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ORDERSERVICE_QNAME, features);
    }

    public OrderService(URL wsdlLocation) {
        super(wsdlLocation, ORDERSERVICE_QNAME);
    }

    public OrderService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ORDERSERVICE_QNAME, features);
    }

    public OrderService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OrderService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns OrderPort
     */
    @WebEndpoint(name = "OrderPort")
    public OrderPort getOrderPort() {
        return super.getPort(new QName("http://www.oldSystem.com/wsdl", "OrderPortPort"), OrderPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OrderPort
     */
    @WebEndpoint(name = "OrderPort")
    public OrderPort getOrderPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.oldSystem.com/wsdl", "OrderPort"), OrderPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ORDERSERVICE_EXCEPTION!= null) {
            throw ORDERSERVICE_EXCEPTION;
        }
        return ORDERSERVICE_WSDL_LOCATION;
    }

}
