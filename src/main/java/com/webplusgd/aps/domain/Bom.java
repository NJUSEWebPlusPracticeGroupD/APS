package com.webplusgd.aps.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bom")
public class Bom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="material_id")
    private Integer materialId;

    @Column(name="standard_capacity")
    private Integer capacity;
    @Column(name="resource_type")
    private Integer resourceType;
    @Column(name="resource_id")
    private Integer resourceId;
    @Column(name="quota")
    private Integer quota;
    @Column(name="craft")
    private String craft;

}
