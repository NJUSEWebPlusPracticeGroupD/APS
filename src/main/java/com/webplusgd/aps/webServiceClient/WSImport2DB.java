package com.webplusgd.aps.webServiceClient;

import com.webplusgd.aps.dao.BomRepository;
import com.webplusgd.aps.dao.OrderRepository;
import com.webplusgd.aps.dao.ResourceRepository;
import com.webplusgd.aps.dao.ShiftRepository;
import com.webplusgd.aps.domain.Bom;
import com.webplusgd.aps.domain.Order;
import com.webplusgd.aps.domain.Resource;
import com.webplusgd.aps.domain.Shift;
import com.webplusgd.aps.webServiceClient.Attendance.Entity.ShiftList;
import com.webplusgd.aps.webServiceClient.Attendance.Entity.ShiftType;
import com.webplusgd.aps.webServiceClient.Attendance.Service.AttendancePortImpl;
import com.webplusgd.aps.webServiceClient.BOM.Entity.BOMList;
import com.webplusgd.aps.webServiceClient.BOM.Entity.BOMType;
import com.webplusgd.aps.webServiceClient.BOM.Entity.ProcessType;
import com.webplusgd.aps.webServiceClient.BOM.Service.BOMPortImpl;
import com.webplusgd.aps.webServiceClient.Erp.Entity.GroupInfoList;
import com.webplusgd.aps.webServiceClient.Erp.Entity.GroupInformationType;
import com.webplusgd.aps.webServiceClient.Erp.Entity.MachineInfoList;
import com.webplusgd.aps.webServiceClient.Erp.Entity.MachineInfoType;
import com.webplusgd.aps.webServiceClient.Erp.Service.ErpPortImpl;
import com.webplusgd.aps.webServiceClient.Order.Entity.OrderInfoType;
import com.webplusgd.aps.webServiceClient.Order.Entity.OrderList;
import com.webplusgd.aps.webServiceClient.Order.Service.OrderPortImpl;

import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * @author toby
 * @create_time 11/3/2020 10:54 AM
 * 导入WebService到数据库
 */
public class WSImport2DB {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    BomRepository bomRepository;
    @Autowired
    ShiftRepository shiftRepository;

    public  void wsImport(){
        //--------order------------------------
        OrderPortImpl orderPort = new OrderPortImpl();
        OrderList orderList = orderPort.getAllOrders();
        System.out.println(orderList.getOrderInfo().size());
        List<OrderInfoType> orderInfoTypes = orderList.getOrderInfo();
        List<Order> orders = new ArrayList<Order>();
        for(OrderInfoType o : orderInfoTypes){
            Order order = new Order();
            order.setOrderId(Integer.parseInt(o.getOrderId()));
            order.setDeliveryDate(xml2Date(o.getTermOfDelivery()));
            order.setMaterialId(Integer.parseInt(o.getItem()));
            order.setOrderCount(((Integer)o.getNumOfOrder()).longValue());
            orders.add(order);
        }
        //orderRepository.saveAll(orders);

        //------------resource------------
        ErpPortImpl erpPort = new ErpPortImpl();
        GroupInfoList groupInfoList = erpPort.getAllGroupInfos();
        List<GroupInformationType> groupInformationTypes = groupInfoList.getGroupInfo();
        MachineInfoList machineInfoList = erpPort.getAllMachineInfos();
        List<MachineInfoType> machineInfoTypes = machineInfoList.getMachineInfo();
        List<Resource> resources = new ArrayList<>();
        for (GroupInformationType g:groupInformationTypes){
            Resource resource = new Resource();
            resource.setId(g.getGroupId());
            resource.setType("班组");
            resource.setCount(g.getAbility());
            resources.add(resource);
        }

        for (MachineInfoType m:machineInfoTypes){
            Resource resource = new Resource();
            resource.setId(m.getName());
            resource.setType(m.getType().value());
            resource.setCount(m.getNumber());
            resources.add(resource);
        }

        //resourceRepository.saveAll(resources);

        //----------shift----------------------
        AttendancePortImpl attendancePort = new AttendancePortImpl();
        ShiftList shiftList = attendancePort.getAllShifts();
        List<Shift> shifts = new ArrayList<>();
        List<ShiftType> shiftTypes = shiftList.getShift();
        Map<String,Integer> codeMap = new HashMap<>();
        codeMap.put("全天",0);
        codeMap.put("早班",1);
        codeMap.put("晚班",2);
        codeMap.put("休息",3);
        Map<String,Integer[]> timeMap = new HashMap<>();
        timeMap.put("全天",new Integer[]{0,24});
        timeMap.put("早班",new Integer[]{7,19});
        timeMap.put("晚班",new Integer[]{19,7});
        timeMap.put("休息",new Integer[]{0,0});
        for(ShiftType s:shiftTypes){
           Shift shift = new Shift();
           shift.setShiftCode(codeMap.get(s.getShiftKind().value()));
           shift.setShiftName(s.getShiftKind().value());
           shift.setStartTime(timeMap.get(s.getShiftKind().value())[0]);
           shift.setEndTime(timeMap.get(s.getShiftKind().value())[1]);
           shifts.add(shift);
        }
        shiftRepository.saveAll(shifts);

        //-----------bom--------------
        List<Bom> boms = new ArrayList<>();
        BOMPortImpl bomPort = new BOMPortImpl();
        BOMList bomList = bomPort.getAllBOMs();
        List<BOMType> bomTypes = bomList.getBOMInfo();
        for(BOMType b:bomTypes){
            boms.addAll(getBoms(b));

        }
        bomRepository.saveAll(boms);

    }

    private Date xml2Date(XMLGregorianCalendar dateType){
        int year = dateType.getYear();
        int month = dateType.getMonth();
        int day = dateType.getDay();
        int hour = dateType.getHour();
        int minute = dateType.getMinute();
        int second = dateType.getSecond();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        Date date = calendar.getTime();
        return date;
    }

    private List<Bom> getBoms(BOMType bomType){
        List<Bom> boms = new ArrayList<>();
        Bom bom = new Bom();
        Integer materialId = Integer.parseInt(bomType.getItemId());
        List<ProcessType> processTypes = bomType.getProcessList().getProcess();
        for(ProcessType p:processTypes){

        }
        return null;
    }
}
