
package com.webplusgd.aps.webServiceClient.Attendance.Service;

import com.webplusgd.aps.webServiceClient.Attendance.Entity.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AttendancePort", targetNamespace = "http://www.oldSystem.com/wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AttendancePort {


    /**
     * 
     * @param getAllGroupSchedulesParam
     * @return
     *     returns Attendance.Entity.GroupScheduleInformationList
     */
    @WebMethod(action = "attendance/getAllGroupSchedules")
    @WebResult(name = "GroupScheduleInformationList", targetNamespace = "http://www.oldCompany.com/attendance", partName = "groupScheduleInfoList")
    @Action(input = "attendance/getAllGroupSchedulesRequest", output = "attendance/getAllGroupSchedulesResponse")
    public GroupScheduleInformationList getAllGroupSchedules(
            @WebParam(name = "getAllGroupSchedulesParam", targetNamespace = "http://www.oldCompany.com/attendance", partName = "getAllGroupSchedulesParam")
                    String getAllGroupSchedulesParam);

    /**
     * 
     * @param getGroupScheduleByIdParam
     * @return
     *     returns Attendance.Entity.GroupScheduleInformationType
     * @throws GroupDoesNotExistFault
     */
    @WebMethod(action = "attendance/gelGroupScheduleById")
    @WebResult(name = "GroupSchedulingInformation", targetNamespace = "http://www.oldCompany.com/attendance", partName = "groupScheduleInfo")
    @Action(input = "attendance/getGroupScheduleByIdRequest", output = "attendance/getGroupScheduleByIdResponse", fault = {
        @FaultAction(className = GroupDoesNotExistFault.class, value = "attendance/GroupDoesNotExistFault")
    })
    public GroupScheduleInformationType getGroupScheduleById(
            @WebParam(name = "getGroupScheduleParam", targetNamespace = "http://www.oldCompany.com/attendance", partName = "getGroupScheduleByIdParam")
                    GetGroupScheduleInformationParamType getGroupScheduleByIdParam)
        throws GroupDoesNotExistFault
    ;

    /**
     * 
     * @param getAllShiftsParam
     * @return
     *     returns Attendance.Entity.ShiftList
     */
    @WebMethod(action = "attendance/getAllShifts")
    @WebResult(name = "shiftList", targetNamespace = "http://www.oldCompany.com/attendance", partName = "shiftList")
    @Action(input = "attendance/getAllShiftsRequest", output = "attendance/getAllShiftsResponse")
    public ShiftList getAllShifts(
            @WebParam(name = "getAllShiftsParam", targetNamespace = "http://www.oldCompany.com/attendance", partName = "getAllShiftsParam")
                    String getAllShiftsParam);

}
