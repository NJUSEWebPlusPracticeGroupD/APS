package com.webplusgd.aps.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "resource_to_shift")
public class ResourceShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "resource_id")
    private Integer resourceId;
    @Column(name = "shift_code")
    private String shiftCode;
    @Column(name = "start_day")
    private int startDay;
    @Column(name = "end_day")
    private int endDay;
}
