package com.webServiceServer.bl;

import com.webServiceServer.Entity.OrderForm.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;

@WebService(name = "OrderFormPort", targetNamespace = "http://www.aps.com/wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface OrderFormPort {
    /**
     *
     * @param getOrderPlanParam
     * @return
     *     returns PersonnelEntity.PersonnelInfoList
     */
    @WebMethod(action = "orderForm/getOrderPlan")
    @WebResult(name = "OrderPlan", targetNamespace = "http://www.aps.com/orderForm", partName = "OrderPlan")
    @Action(input = "orderForm/getOrderPlanRequest", output = "orderForm/getOrderPlanRequestResponse")
    public WsOrderPlanItemList getOrderPlan(
            @WebParam(name = "getOrderPlanParam", targetNamespace = "http://www.aps.com/orderForm", partName = "getOrderPlanParam")
                    String getOrderPlanParam);

}
