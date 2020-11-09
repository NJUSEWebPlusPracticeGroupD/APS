
package com.webplusgd.aps.webServiceClient.Order.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;

import com.webplusgd.aps.webServiceClient.Order.Entity.GetOrderByIdType;
import com.webplusgd.aps.webServiceClient.Order.Entity.GetOrdersByItemIdType;
import com.webplusgd.aps.webServiceClient.Order.Entity.OrderInfoType;
import com.webplusgd.aps.webServiceClient.Order.Entity.OrderList;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "OrderPort", targetNamespace = "http://www.oldSystem.com/wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface OrderPort {


    /**
     * 
     * @param getAllOrdersParam
     * @return
     *     returns com.oldcompany.order.OrderList
     */
    @WebMethod(action = "order/getAllOrders")
    @WebResult(name = "orderList", targetNamespace = "http://www.oldCompany.com/order", partName = "orderList")
    @Action(input = "order/getAllOrdersRequest", output = "order/getAllOrdersResponse")
    public OrderList getAllOrders(
            @WebParam(name = "getAllOrdersParam", targetNamespace = "http://www.oldCompany.com/order", partName = "getAllOrdersParam")
                    String getAllOrdersParam);

    /**
     * 
     * @param getOrderByIdParam
     * @return
     *     returns com.oldcompany.order.OrderInfoType
     * @throws OrderDoesNotExistFault
     */
    @WebMethod(action = "order/getOrderById")
    @WebResult(name = "orderInfo", targetNamespace = "http://www.oldCompany.com/order", partName = "orderInfo")
    @Action(input = "order/getOrderByIdRequest", output = "order/getOrderByIdResponse", fault = {
        @FaultAction(className = OrderDoesNotExistFault.class, value = "order/OrderDoesNotExistFault")
    })
    public OrderInfoType getOrderById(
            @WebParam(name = "getOrderByIdParam", targetNamespace = "http://www.oldCompany.com/order", partName = "getOrderByIdParam")
                    GetOrderByIdType getOrderByIdParam)
        throws OrderDoesNotExistFault
    ;

    /**
     * 
     * @param getOrdersByMaterielIdParam
     * @return
     *     returns com.oldcompany.order.OrderList
     * @throws ItemDoesNotExistFault
     */
    @WebMethod(action = "order/getOrdersByMaterielId")
    @WebResult(name = "orderList", targetNamespace = "http://www.oldCompany.com/order", partName = "orderList")
    @Action(input = "order/getOrdersByMaterielIdRequest", output = "order/getOrdersByMaterielIdResponse", fault = {
        @FaultAction(className = ItemDoesNotExistFault.class, value = "order/ItemDoesNotExistFault")
    })
    public OrderList getOrdersByItemId(
            @WebParam(name = "getOrdersByItemIdParam", targetNamespace = "http://www.oldCompany.com/order", partName = "getOrdersByMaterielIdParam")
                    GetOrdersByItemIdType getOrdersByMaterielIdParam)
        throws ItemDoesNotExistFault
    ;

}
