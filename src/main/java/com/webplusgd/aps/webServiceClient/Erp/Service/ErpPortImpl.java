package com.webplusgd.aps.webServiceClient.Erp.Service;

import com.webplusgd.aps.webServiceClient.Erp.Entity.*;


import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

public class ErpPortImpl{
    
    public GroupInfoList getAllGroupInfos() {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        GroupInfoList res = erpPort.getAllGroupInfos("getAllGroupInfosParam");

        return res;
    }

    
    public GroupInformationType getGroupInfoById(GetGroupInfoByIdParamType getGroupInfoByIdParam) {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        try {
           GroupInformationType res =  erpPort.getGroupInfoById(getGroupInfoByIdParam);
           return res;
        }
        catch (GroupDoesNotExistFault e){
            e.printStackTrace();
            return null;
        }

    }

    
    public GroupInformationType getGroupInfoByMemberName(GetGroupInfoByMemberNameParamType getGroupInfoByMemberNameParam) {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        try {
            GroupInformationType res =  erpPort.getGroupInfoByMemberName(getGroupInfoByMemberNameParam);
            return res;
        }
        catch (GroupDoesNotExistFault e){
            e.printStackTrace();
            return null;
        }

    }

    
    public MachineInfoList getAllMachineInfos() {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        MachineInfoList res =  erpPort.getAllMachineInfos("getAllMachineInfos");
        return res;


    }

    
    public MachineInfoType getMachineInfoByName(GetMachineInfoByNameParamType getMachineInfoByNameParam) {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        try{
            MachineInfoType res =  erpPort.getMachineInfoByName(getMachineInfoByNameParam);
            return res;
        }
        catch (MachineDoesNotExistFault e){
            e.printStackTrace();
            return null;
        }


    }

    
    public MachineInfoList getMachineInfosByType(GetMachineInfosByTypeParamType getMachineInfosByTypeParam) {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        MachineInfoList res = erpPort.getMachineInfosByType(getMachineInfosByTypeParam);


        return res;
    }

    
    public ItemInfoList getAllItemInfos(String getAllItemInfosParam) {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        ItemInfoList res = erpPort.getAllItemInfos("getAllItemInfosParam");
        return res;
    }

    
    public ItemInfoType getItemInfoById(GetItemInfoByIdParamType getItemInfoByIdParam)  {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/erp" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        ErpService erpService = new ErpService();
        ErpPort erpPort = erpService.getErpPort();
        BindingProvider bindingProvider = (BindingProvider)erpPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/erp" + "?wsdl" );
        try {
            ItemInfoType res = erpPort.getItemInfoById(getItemInfoByIdParam);
            return res;
        }
        catch (ItemDoesNotExistFault e){
            e.printStackTrace();
            return null;
        }

    }
}
