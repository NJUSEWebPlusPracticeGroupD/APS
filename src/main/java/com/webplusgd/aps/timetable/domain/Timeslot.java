package com.webplusgd.aps.timetable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * @author Rollingegg
 * @create_time 10/16/2020 10:23 AM
 */
@Data
@NoArgsConstructor
public class Timeslot {
    @Schema(example = "Monday", description = "星期几")
    private DayOfWeek dayOfWeek;
    @Schema(example = "9:00", description = "上课时间")
    private LocalTime startTime;
    @Schema(example = "10:00", description = "下课时间")
    private LocalTime endTime;
    public Timeslot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return dayOfWeek + " " + startTime.toString();
    }
}
