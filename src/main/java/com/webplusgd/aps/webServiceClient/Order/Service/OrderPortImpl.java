package com.webplusgd.aps.webServiceClient.Order.Service;


import com.webplusgd.aps.webServiceClient.Order.Entity.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OrderPortImpl  {


    public OrderList getOrdersByItemId(GetOrdersByItemIdType getOrdersByMaterielIdParam)  {
        URL url = null;
        try{
             url = new URL("http://localhost:8088/ws/order" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        OrderService orderService = new OrderService(url);
        OrderPort orderPort = orderService.getOrderPort();

        try{
            OrderList res = orderPort.getOrdersByItemId(getOrdersByMaterielIdParam);
            return res;
        } catch (ItemDoesNotExistFault itemDoesNotExistFault) {
            itemDoesNotExistFault.printStackTrace();
            OrderList res = new OrderList();
            res.setOrderInfo(new ArrayList<OrderInfoType>());
            return res;
        }

    }


    public OrderList getAllOrders()  {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/order" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        OrderService orderService = new OrderService(url);
        OrderPort orderPort = orderService.getOrderPort();


        OrderList res = orderPort.getAllOrders("getAllOrdersParam");

        return res;

    }


    public OrderInfoType getOrderById(GetOrderByIdType getOrderByIdParam)  {
        URL url = null;
        try{
            url = new URL("http://localhost:8088/ws/order" + "?wsdl");
        }
        catch ( MalformedURLException e){
            System.out.println("url exception");
            return null;
        }
        OrderService orderService = new OrderService(url);
        OrderPort orderPort = orderService.getOrderPort();
        try {
            OrderInfoType res = orderPort.getOrderById(getOrderByIdParam);
            return res;
        }
        catch (OrderDoesNotExistFault e){
            e.printStackTrace();
            return null;
        }

    }
}
