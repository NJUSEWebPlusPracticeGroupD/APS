package com.webplusgd.aps.webServiceClient;

import com.webplusgd.aps.dao.BomRepository;
import com.webplusgd.aps.dao.OrderRepository;
import com.webplusgd.aps.dao.ResourceRepository;
import com.webplusgd.aps.dao.ShiftRepository;
import com.webplusgd.aps.domain.Bom;
import com.webplusgd.aps.domain.Order;
import com.webplusgd.aps.domain.Resource;
import com.webplusgd.aps.domain.Shift;
import com.webplusgd.aps.webServiceClient.Attendance.Entity.GroupScheduleInformationList;
import com.webplusgd.aps.webServiceClient.Attendance.Entity.GroupScheduleInformationType;
import com.webplusgd.aps.webServiceClient.Attendance.Entity.ShiftList;
import com.webplusgd.aps.webServiceClient.Attendance.Entity.ShiftType;
import com.webplusgd.aps.webServiceClient.Attendance.Service.AttendancePortImpl;
import com.webplusgd.aps.webServiceClient.BOM.Entity.BOMList;
import com.webplusgd.aps.webServiceClient.BOM.Entity.BOMType;
import com.webplusgd.aps.webServiceClient.BOM.Entity.ProcessType;
import com.webplusgd.aps.webServiceClient.BOM.Entity.ResourceInfoType;
import com.webplusgd.aps.webServiceClient.BOM.Service.BOMPortImpl;
import com.webplusgd.aps.webServiceClient.Erp.Entity.GroupInfoList;
import com.webplusgd.aps.webServiceClient.Erp.Entity.GroupInformationType;
import com.webplusgd.aps.webServiceClient.Erp.Entity.MachineInfoList;
import com.webplusgd.aps.webServiceClient.Erp.Entity.MachineInfoType;
import com.webplusgd.aps.webServiceClient.Erp.Service.ErpPortImpl;
import com.webplusgd.aps.webServiceClient.Order.Entity.OrderInfoType;
import com.webplusgd.aps.webServiceClient.Order.Entity.OrderList;
import com.webplusgd.aps.webServiceClient.Order.Service.OrderPortImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import java.util.*;

/**
 * @author toby
 * @create_time 11/3/2020 10:54 AM
 * 导入WebService到数据库
 */
@Slf4j
@Component
public class WSImport2DB implements ApplicationRunner {

    private OrderRepository orderRepository;

    private ResourceRepository resourceRepository;

    private BomRepository bomRepository;

    private ShiftRepository shiftRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setBomRepository(BomRepository bomRepository) {
        this.bomRepository = bomRepository;
    }

    @Autowired
    public void setShiftRepository(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Autowired
    public void setResourceRepository(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Autowired
    private ConfigurableApplicationContext context;

    public void wsImport() {


        //--------order------------------------
        OrderPortImpl orderPort = new OrderPortImpl();
        OrderList orderList = orderPort.getAllOrders();

        List<OrderInfoType> orderInfoTypes = orderList.getOrderInfo();
        OrderInfoType[] orderInfoTypes1 = orderList.getOrderInfoTypes();
        List<Order> orders = new ArrayList<Order>();
        for (OrderInfoType o : orderInfoTypes1) {
            Order order = new Order();
            order.setOrderId(Integer.parseInt(o.getOrderId()));
            order.setDeliveryDate(xml2Date(o.getTermOfDelivery()));
            order.setMaterialId(Integer.parseInt(o.getItem()));
            order.setOrderCount(((Integer) o.getNumOfOrder()).longValue());
            orders.add(order);
        }


        //----------shift----------------------
        AttendancePortImpl attendancePort = new AttendancePortImpl();
        ShiftList shiftList = attendancePort.getAllShifts();
        List<Shift> shifts = new ArrayList<>();
        List<ShiftType> shiftTypes = shiftList.getShift();
        Map<String, Integer> codeMap = new HashMap<>();
        codeMap.put("全天", 0);
        codeMap.put("早班", 1);
        codeMap.put("晚班", 2);
        codeMap.put("休息", 3);
        Map<String, Integer[]> timeMap = new HashMap<>();
        timeMap.put("全天", new Integer[]{0, 24});
        timeMap.put("早班", new Integer[]{7, 19});
        timeMap.put("晚班", new Integer[]{19, 7});
        timeMap.put("休息", new Integer[]{0, 0});
        for (ShiftType s : shiftTypes) {
            Shift shift = new Shift();
            shift.setShiftCode(codeMap.get(s.getShiftKind().value()));
            shift.setShiftName(s.getShiftKind().value());
            shift.setStartTime(timeMap.get(s.getShiftKind().value())[0]);
            shift.setEndTime(timeMap.get(s.getShiftKind().value())[1]);
            shifts.add(shift);
        }


        //------------resource------------
        ErpPortImpl erpPort = new ErpPortImpl();
        GroupInfoList groupInfoList = erpPort.getAllGroupInfos();
        List<GroupInformationType> groupInformationTypes = groupInfoList.getGroupInfo();
        MachineInfoList machineInfoList = erpPort.getAllMachineInfos();
        List<MachineInfoType> machineInfoTypes = machineInfoList.getMachineInfo();
        GroupScheduleInformationList groupScheduleInformationList = attendancePort.getAllGroupSchedules();
        List<GroupScheduleInformationType> groupScheduleInformationTypes = groupScheduleInformationList.getGroupScheduleInformation();

        List<Resource> resources = new ArrayList<>();
        for (GroupInformationType g : groupInformationTypes) {
            Resource resource = new Resource();
            resource.setId(g.getGroupName());
            resource.setType("班组");
            resource.setCount(g.getAbility());

            GroupScheduleInformationType curGs = null;
            for (GroupScheduleInformationType gs : groupScheduleInformationTypes) {
                if (gs.getGroupInformation().getGroupName().equals(g.getGroupName())) {
                    curGs = gs;
                    break;
                }
            }
            if (curGs == null) {
                System.out.println("gs does not exist");
                return;
            }

            resource.setShiftCode(codeMap.get(curGs.getShift().getShiftKind().value()));
            resource.setStartDay(1);
            resource.setEndDay(5);
            resources.add(resource);
        }

        for (MachineInfoType m : machineInfoTypes) {
            Resource resource = new Resource();
            resource.setId(m.getName());
            if (m.getType().value().equals("line")) {
                resource.setType("线体");
            } else {
                resource.setType("设备");
            }

            resource.setCount(m.getNumber());

            resource.setShiftCode(0);
            if (m.getName().contains("line")) {
                resource.setStartDay(1);
                resource.setEndDay(5);
            } else {
                resource.setStartDay(1);
                resource.setEndDay(7);
            }

            resources.add(resource);
        }


        //-----------bom--------------
        List<Bom> boms = new ArrayList<>();
        BOMPortImpl bomPort = new BOMPortImpl();
        BOMList bomList = bomPort.getAllBOMs();
        List<BOMType> bomTypes = bomList.getBOMInfo();
        for (BOMType b : bomTypes) {
            boms.addAll(getBoms(b));

        }

        log.info("Web Service 导入 Database 完成");

        orderRepository.saveAll(orders);
        resourceRepository.saveAll(resources);
        shiftRepository.saveAll(shifts);
        bomRepository.saveAll(boms);
    }

    private Date xml2Date(XMLGregorianCalendar dateType) {
        int year = dateType.getYear();
        int month = dateType.getMonth();
        int day = dateType.getDay();


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date date = calendar.getTime();
        return date;
    }

    private List<Bom> getBoms(BOMType bomType) {
        List<Bom> boms = new ArrayList<>();

        Integer materialId = Integer.parseInt(bomType.getItemId());
        List<ProcessType> processTypes = bomType.getProcessList().getProcess();
        for (ProcessType p : processTypes) {
            List<ResourceInfoType> alterGroups = p.getAlternativeGroupInfoList().getAlternativeGroupInfo();
            List<ResourceInfoType> alterMachines = p.getAlternativeMachineInfoList().getAlternativeMachineInfo();
            for (ResourceInfoType resourceInfo : alterGroups) {
                Bom bom = new Bom();
                bom.setMaterialId(materialId);
                bom.setCapacity(resourceInfo.getStandardCapacity());
                bom.setCraft(p.getProcessName());
                bom.setQuota(p.getNumOfPersonnel());
                bom.setResourceId(resourceInfo.getResourceName());
                bom.setResourceType(0);
                bom.setSwitchingTime(resourceInfo.getTimeOfChangeLine());

                boms.add(bom);
            }

            for (ResourceInfoType resourceInfo : alterMachines) {
                Bom bom = new Bom();
                bom.setMaterialId(materialId);
                bom.setCapacity(resourceInfo.getStandardCapacity());
                bom.setCraft(p.getProcessName());
                bom.setQuota(p.getNumOfPersonnel());
                bom.setResourceId(resourceInfo.getResourceName());
                bom.setResourceType(1);
                bom.setSwitchingTime(resourceInfo.getTimeOfChangeLine());

                boms.add(bom);
            }
        }
        return boms;
    }


    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("清空原来的数据库中······");
            truncateTable();
            log.info("Web Service导入数据库中······");
            wsImport();
        } catch (Exception e) {
            log.error("WebService导入数据库失败, {}", e.getMessage());
            if (e instanceof WebServiceException) {
                log.error("WebService启动异常，请确保遗留系统已经启动或者检查WSDL");
            }
            // 关闭APS系统
            context.close();
        }
    }

    private void truncateTable(){
        orderRepository.truncateTable();
        bomRepository.truncateTable();
        resourceRepository.truncateTable();
        shiftRepository.truncateTable();
    }
}
