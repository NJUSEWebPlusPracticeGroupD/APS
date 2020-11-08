package com.webplusgd.aps.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "material_id")
    private Integer materialId;
    @Column(name = "order_count")
    private Long orderCount;
    @Column(name = "delivery_date")
    private Date deliveryDate;
}
