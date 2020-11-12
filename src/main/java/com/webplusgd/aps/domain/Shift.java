package com.webplusgd.aps.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shift")
public class Shift {

    @Id
    @Column(name = "shift_code")
    private Integer shiftCode;
    @Column(name = "shift_name")
    private String shiftName;
    @Column(name = "start_time")
    private Integer startTime;
    @Column(name = "end_time")
    private Integer endTime;

}
