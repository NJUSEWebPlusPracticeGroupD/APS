package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.dao.BomRepository;
import com.webplusgd.aps.dao.OrderRepository;
import com.webplusgd.aps.dao.ResourceRepository;
import optaplanner.ApsSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rollingegg
 * @create_time 11/9/2020 10:15 PM
 * 从数据库导出Solution所需的数据，包括Order, Resource, Bom, TimeslotList
 */
@Component
public class DataManager {
    private OrderRepository orderRepository;

    private ResourceRepository resourceRepository;

    private BomRepository bomRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setBomRepository(BomRepository bomRepository) {
        this.bomRepository = bomRepository;
    }

    @Autowired
    public void setResourceRepository(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public ApsSolution getProblemDataset(){
        return new ApsSolution();
    }
}
