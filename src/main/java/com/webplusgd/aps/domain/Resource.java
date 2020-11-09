package com.webplusgd.aps.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@NoArgsConstructor
@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @Column(name="resource_id")
    private Integer id;
    @Column(name="resource_type")
    private String type;
    @Column(name="resource_count")
    private Integer count;
    @Column(name = "shift_code")
    private Integer shiftCode;
    @Column(name = "start_day")
    private Integer startDay;
    @Column(name = "end_day")
    private Integer endDay;
}
