
package com.webplusgd.aps.webServiceClient.Attendance.Service;

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
@WebServiceClient(name = "AttendanceService", targetNamespace = "http://www.oldSystem.com/wsdl", wsdlLocation = "file:/D:/oceania-iter2/webclientTest/xml/wsdl/Attendance.wsdl")
public class AttendanceService
    extends Service
{

    private final static URL ATTENDANCESERVICE_WSDL_LOCATION;
    private final static WebServiceException ATTENDANCESERVICE_EXCEPTION;
    private final static QName ATTENDANCESERVICE_QNAME = new QName("http://www.oldSystem.com/wsdl", "AttendanceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/oceania-iter2/webclientTest/xml/wsdl/Attendance.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ATTENDANCESERVICE_WSDL_LOCATION = url;
        ATTENDANCESERVICE_EXCEPTION = e;
    }

    public AttendanceService() {
        super(__getWsdlLocation(), ATTENDANCESERVICE_QNAME);
    }

    public AttendanceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ATTENDANCESERVICE_QNAME, features);
    }

    public AttendanceService(URL wsdlLocation) {
        super(wsdlLocation, ATTENDANCESERVICE_QNAME);
    }

    public AttendanceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ATTENDANCESERVICE_QNAME, features);
    }

    public AttendanceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AttendanceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AttendancePort
     */
    @WebEndpoint(name = "AttendancePort")
    public AttendancePort getAttendancePort() {
        return super.getPort(new QName("http://www.oldSystem.com/wsdl", "AttendancePort"), AttendancePort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AttendancePort
     */
    @WebEndpoint(name = "AttendancePort")
    public AttendancePort getAttendancePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.oldSystem.com/wsdl", "AttendancePort"), AttendancePort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ATTENDANCESERVICE_EXCEPTION!= null) {
            throw ATTENDANCESERVICE_EXCEPTION;
        }
        return ATTENDANCESERVICE_WSDL_LOCATION;
    }

}