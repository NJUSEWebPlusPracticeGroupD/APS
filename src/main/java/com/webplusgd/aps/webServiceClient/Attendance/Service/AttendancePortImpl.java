package com.webplusgd.aps.webServiceClient.Attendance.Service;

import com.webplusgd.aps.webServiceClient.Attendance.Entity.*;

import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

public class AttendancePortImpl {

    public GroupScheduleInformationList getAllGroupSchedules() {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/attendance" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
       AttendanceService attendanceService = new AttendanceService();
        AttendancePort attendancePort = attendanceService.getAttendancePort();
        BindingProvider bindingProvider = (BindingProvider)attendancePort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/attendance" + "?wsdl" );

        GroupScheduleInformationList res = attendancePort.getAllGroupSchedules("getAllGroupSchedulesParam");

        return res;
    }


    public GroupScheduleInformationType getGroupScheduleById(GetGroupScheduleInformationParamType getGroupScheduleByIdParam) {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/attendance" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        AttendanceService attendanceService = new AttendanceService();
        AttendancePort attendancePort = attendanceService.getAttendancePort();
        BindingProvider bindingProvider = (BindingProvider)attendancePort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/attendance" + "?wsdl" );
        try {
            GroupScheduleInformationType res = attendancePort.getGroupScheduleById(getGroupScheduleByIdParam);
        }
        catch (GroupDoesNotExistFault e){
            System.out.println("小组不存在");
            return null;
        }
        return null;
    }


    public ShiftList getAllShifts() {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/attendance" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        AttendanceService attendanceService = new AttendanceService();
        AttendancePort attendancePort = attendanceService.getAttendancePort();
        BindingProvider bindingProvider = (BindingProvider)attendancePort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/attendance" + "?wsdl" );

        ShiftList res = attendancePort.getAllShifts("getAllShiftsParam");


        return res;
    }
}
