
package com.webplusgd.aps.webServiceClient.Erp.Entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the Erp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OrderDoesNotExistFault_QNAME = new QName("http://www.oldCompany.com/fault", "OrderDoesNotExistFault");
    private final static QName _GetAllOrdersParam_QNAME = new QName("http://www.oldCompany.com/order", "getAllOrdersParam");
    private final static QName _ItemDoesNotExistFault_QNAME = new QName("http://www.oldCompany.com/fault", "ItemDoesNotExistFault");
    private final static QName _GetAllMachineInfosParam_QNAME = new QName("http://www.oldCompany.com/erp", "getAllMachineInfosParam");
    private final static QName _GetGroupInfoByIdParam_QNAME = new QName("http://www.oldCompany.com/erp", "getGroupInfoByIdParam");
    private final static QName _PersonnelDoesNotExistFault_QNAME = new QName("http://www.oldCompany.com/fault", "PersonnelDoesNotExistFault");
    private final static QName _GroupDoesNotExistFault_QNAME = new QName("http://www.oldCompany.com/fault", "GroupDoesNotExistFault");
    private final static QName _OrderInfo_QNAME = new QName("http://www.oldCompany.com/order", "orderInfo");
    private final static QName _GetMachineInfosByTypeParam_QNAME = new QName("http://www.oldCompany.com/erp", "getMachineInfosByTypeParam");
    private final static QName _GetItemInfoByIdParam_QNAME = new QName("http://www.oldCompany.com/erp", "getItemInfoByIdParam");
    private final static QName _MachineInfo_QNAME = new QName("http://www.oldCompany.com/erp", "MachineInfo");
    private final static QName _GetAllItemInfosParam_QNAME = new QName("http://www.oldCompany.com/erp", "getAllItemInfosParam");
    private final static QName _MachineDoesNotExistFault_QNAME = new QName("http://www.oldCompany.com/fault", "MachineDoesNotExistFault");
    private final static QName _GetOrderByIdParam_QNAME = new QName("http://www.oldCompany.com/order", "getOrderByIdParam");
    private final static QName _ItemInfo_QNAME = new QName("http://www.oldCompany.com/erp", "ItemInfo");
    private final static QName _GroupInfo_QNAME = new QName("http://www.oldCompany.com/erp", "GroupInfo");
    private final static QName _GetGroupInfoByMemberNameParam_QNAME = new QName("http://www.oldCompany.com/erp", "getGroupInfoByMemberNameParam");
    private final static QName _GetMachineInfoByNameParam_QNAME = new QName("http://www.oldCompany.com/erp", "getMachineInfoByNameParam");
    private final static QName _MaterielDoesNotExistFault_QNAME = new QName("http://www.oldCompany.com/fault", "MaterielDoesNotExistFault");
    private final static QName _GetAllGroupInfosParam_QNAME = new QName("http://www.oldCompany.com/erp", "getAllGroupInfosParam");
    private final static QName _GetOrdersByItemIdParam_QNAME = new QName("http://www.oldCompany.com/order", "getOrdersByItemIdParam");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: Erp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetGroupInfoByMemberNameParamType }
     * 
     */
    public GetGroupInfoByMemberNameParamType createGetGroupInfoByMemberNameParamType() {
        return new GetGroupInfoByMemberNameParamType();
    }

    /**
     * Create an instance of {@link GetMachineInfoByNameParamType }
     * 
     */
    public GetMachineInfoByNameParamType createGetMachineInfoByNameParamType() {
        return new GetMachineInfoByNameParamType();
    }

    /**
     * Create an instance of {@link GroupInfoList }
     * 
     */
    public GroupInfoList createGroupInfoList() {
        return new GroupInfoList();
    }

    /**
     * Create an instance of {@link GroupInformationType }
     * 
     */
    public GroupInformationType createGroupInformationType() {
        return new GroupInformationType();
    }

    /**
     * Create an instance of {@link ItemInfoType }
     * 
     */
    public ItemInfoType createItemInfoType() {
        return new ItemInfoType();
    }

    /**
     * Create an instance of {@link ItemInfoList }
     * 
     */
    public ItemInfoList createItemInfoList() {
        return new ItemInfoList();
    }

    /**
     * Create an instance of {@link GetMachineInfosByTypeParamType }
     * 
     */
    public GetMachineInfosByTypeParamType createGetMachineInfosByTypeParamType() {
        return new GetMachineInfosByTypeParamType();
    }

    /**
     * Create an instance of {@link GetItemInfoByIdParamType }
     * 
     */
    public GetItemInfoByIdParamType createGetItemInfoByIdParamType() {
        return new GetItemInfoByIdParamType();
    }

    /**
     * Create an instance of {@link MachineInfoType }
     * 
     */
    public MachineInfoType createMachineInfoType() {
        return new MachineInfoType();
    }

    /**
     * Create an instance of {@link GetGroupInfoByIdParamType }
     * 
     */
    public GetGroupInfoByIdParamType createGetGroupInfoByIdParamType() {
        return new GetGroupInfoByIdParamType();
    }

    /**
     * Create an instance of {@link MachineInfoList }
     * 
     */
    public MachineInfoList createMachineInfoList() {
        return new MachineInfoList();
    }

    /**
     * Create an instance of {@link MaterielDoesNotExistFaultType }
     * 
     */
    public MaterielDoesNotExistFaultType createMaterielDoesNotExistFaultType() {
        return new MaterielDoesNotExistFaultType();
    }

    /**
     * Create an instance of {@link GroupDoesNotExistFaultType }
     * 
     */
    public GroupDoesNotExistFaultType createGroupDoesNotExistFaultType() {
        return new GroupDoesNotExistFaultType();
    }

    /**
     * Create an instance of {@link MachineDoesNotExistFaultType }
     * 
     */
    public MachineDoesNotExistFaultType createMachineDoesNotExistFaultType() {
        return new MachineDoesNotExistFaultType();
    }

    /**
     * Create an instance of {@link OrderDoesNotExistFaultType }
     * 
     */
    public OrderDoesNotExistFaultType createOrderDoesNotExistFaultType() {
        return new OrderDoesNotExistFaultType();
    }

    /**
     * Create an instance of {@link ItemDoesNotExistFaultType }
     * 
     */
    public ItemDoesNotExistFaultType createItemDoesNotExistFaultType() {
        return new ItemDoesNotExistFaultType();
    }

    /**
     * Create an instance of {@link PersonnelDoesNotExistFaultType }
     * 
     */
    public PersonnelDoesNotExistFaultType createPersonnelDoesNotExistFaultType() {
        return new PersonnelDoesNotExistFaultType();
    }

    /**
     * Create an instance of {@link GetOrderByIdType }
     * 
     */
    public GetOrderByIdType createGetOrderByIdType() {
        return new GetOrderByIdType();
    }

    /**
     * Create an instance of {@link OrderInfoType }
     * 
     */
    public OrderInfoType createOrderInfoType() {
        return new OrderInfoType();
    }

    /**
     * Create an instance of {@link OrderList }
     * 
     */
    public OrderList createOrderList() {
        return new OrderList();
    }

    /**
     * Create an instance of {@link GetOrdersByItemIdType }
     * 
     */
    public GetOrdersByItemIdType createGetOrdersByItemIdType() {
        return new GetOrdersByItemIdType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderDoesNotExistFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/fault", name = "OrderDoesNotExistFault")
    public JAXBElement<OrderDoesNotExistFaultType> createOrderDoesNotExistFault(OrderDoesNotExistFaultType value) {
        return new JAXBElement<OrderDoesNotExistFaultType>(_OrderDoesNotExistFault_QNAME, OrderDoesNotExistFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/order", name = "getAllOrdersParam", defaultValue = "getAllOrders")
    public JAXBElement<String> createGetAllOrdersParam(String value) {
        return new JAXBElement<String>(_GetAllOrdersParam_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemDoesNotExistFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/fault", name = "ItemDoesNotExistFault")
    public JAXBElement<ItemDoesNotExistFaultType> createItemDoesNotExistFault(ItemDoesNotExistFaultType value) {
        return new JAXBElement<ItemDoesNotExistFaultType>(_ItemDoesNotExistFault_QNAME, ItemDoesNotExistFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getAllMachineInfosParam")
    public JAXBElement<String> createGetAllMachineInfosParam(String value) {
        return new JAXBElement<String>(_GetAllMachineInfosParam_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupInfoByIdParamType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getGroupInfoByIdParam")
    public JAXBElement<GetGroupInfoByIdParamType> createGetGroupInfoByIdParam(GetGroupInfoByIdParamType value) {
        return new JAXBElement<GetGroupInfoByIdParamType>(_GetGroupInfoByIdParam_QNAME, GetGroupInfoByIdParamType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonnelDoesNotExistFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/fault", name = "PersonnelDoesNotExistFault")
    public JAXBElement<PersonnelDoesNotExistFaultType> createPersonnelDoesNotExistFault(PersonnelDoesNotExistFaultType value) {
        return new JAXBElement<PersonnelDoesNotExistFaultType>(_PersonnelDoesNotExistFault_QNAME, PersonnelDoesNotExistFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupDoesNotExistFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/fault", name = "GroupDoesNotExistFault")
    public JAXBElement<GroupDoesNotExistFaultType> createGroupDoesNotExistFault(GroupDoesNotExistFaultType value) {
        return new JAXBElement<GroupDoesNotExistFaultType>(_GroupDoesNotExistFault_QNAME, GroupDoesNotExistFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/order", name = "orderInfo")
    public JAXBElement<OrderInfoType> createOrderInfo(OrderInfoType value) {
        return new JAXBElement<OrderInfoType>(_OrderInfo_QNAME, OrderInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMachineInfosByTypeParamType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getMachineInfosByTypeParam")
    public JAXBElement<GetMachineInfosByTypeParamType> createGetMachineInfosByTypeParam(GetMachineInfosByTypeParamType value) {
        return new JAXBElement<GetMachineInfosByTypeParamType>(_GetMachineInfosByTypeParam_QNAME, GetMachineInfosByTypeParamType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemInfoByIdParamType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getItemInfoByIdParam")
    public JAXBElement<GetItemInfoByIdParamType> createGetItemInfoByIdParam(GetItemInfoByIdParamType value) {
        return new JAXBElement<GetItemInfoByIdParamType>(_GetItemInfoByIdParam_QNAME, GetItemInfoByIdParamType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MachineInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "MachineInfo")
    public JAXBElement<MachineInfoType> createMachineInfo(MachineInfoType value) {
        return new JAXBElement<MachineInfoType>(_MachineInfo_QNAME, MachineInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getAllItemInfosParam")
    public JAXBElement<String> createGetAllItemInfosParam(String value) {
        return new JAXBElement<String>(_GetAllItemInfosParam_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MachineDoesNotExistFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/fault", name = "MachineDoesNotExistFault")
    public JAXBElement<MachineDoesNotExistFaultType> createMachineDoesNotExistFault(MachineDoesNotExistFaultType value) {
        return new JAXBElement<MachineDoesNotExistFaultType>(_MachineDoesNotExistFault_QNAME, MachineDoesNotExistFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrderByIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/order", name = "getOrderByIdParam")
    public JAXBElement<GetOrderByIdType> createGetOrderByIdParam(GetOrderByIdType value) {
        return new JAXBElement<GetOrderByIdType>(_GetOrderByIdParam_QNAME, GetOrderByIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "ItemInfo")
    public JAXBElement<ItemInfoType> createItemInfo(ItemInfoType value) {
        return new JAXBElement<ItemInfoType>(_ItemInfo_QNAME, ItemInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupInformationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "GroupInfo")
    public JAXBElement<GroupInformationType> createGroupInfo(GroupInformationType value) {
        return new JAXBElement<GroupInformationType>(_GroupInfo_QNAME, GroupInformationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupInfoByMemberNameParamType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getGroupInfoByMemberNameParam")
    public JAXBElement<GetGroupInfoByMemberNameParamType> createGetGroupInfoByMemberNameParam(GetGroupInfoByMemberNameParamType value) {
        return new JAXBElement<GetGroupInfoByMemberNameParamType>(_GetGroupInfoByMemberNameParam_QNAME, GetGroupInfoByMemberNameParamType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMachineInfoByNameParamType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getMachineInfoByNameParam")
    public JAXBElement<GetMachineInfoByNameParamType> createGetMachineInfoByNameParam(GetMachineInfoByNameParamType value) {
        return new JAXBElement<GetMachineInfoByNameParamType>(_GetMachineInfoByNameParam_QNAME, GetMachineInfoByNameParamType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MaterielDoesNotExistFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/fault", name = "MaterielDoesNotExistFault")
    public JAXBElement<MaterielDoesNotExistFaultType> createMaterielDoesNotExistFault(MaterielDoesNotExistFaultType value) {
        return new JAXBElement<MaterielDoesNotExistFaultType>(_MaterielDoesNotExistFault_QNAME, MaterielDoesNotExistFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/erp", name = "getAllGroupInfosParam")
    public JAXBElement<String> createGetAllGroupInfosParam(String value) {
        return new JAXBElement<String>(_GetAllGroupInfosParam_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrdersByItemIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oldCompany.com/order", name = "getOrdersByItemIdParam")
    public JAXBElement<GetOrdersByItemIdType> createGetOrdersByItemIdParam(GetOrdersByItemIdType value) {
        return new JAXBElement<GetOrdersByItemIdType>(_GetOrdersByItemIdParam_QNAME, GetOrdersByItemIdType.class, null, value);
    }

}
