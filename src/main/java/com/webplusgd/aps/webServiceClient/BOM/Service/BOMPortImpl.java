package com.webplusgd.aps.webServiceClient.BOM.Service;

import com.webplusgd.aps.webServiceClient.BOM.Entity.*;


import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

public class BOMPortImpl{

    public BOMList getAllBOMs() {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/bom" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        BOMService bomService = new BOMService();
        BOMPort bomPort = bomService.getBOMPort();
        BindingProvider bindingProvider = (BindingProvider)bomPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/bom" + "?wsdl" );

        BOMList res = bomPort.getAllBOMs("getAllBOMsParam");
        return res;
    }


    public BOMType getBOMByItemId(GetBOMByItemIdParamType getBOMByItemIdParam)  {

        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/bom" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        BOMService bomService = new BOMService();
        BOMPort bomPort = bomService.getBOMPort();
        BindingProvider bindingProvider = (BindingProvider)bomPort;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8088/ws/bom" + "?wsdl" );
        try {
            BOMType res = bomPort.getBOMByItemId(getBOMByItemIdParam);
            return res;
        }
        catch (ItemDoesNotExistFault e){
            System.out.println("产品不存在");
            return null;
        }

    }
}
