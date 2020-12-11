package com.webplusgd.aps.optaplanner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * @author Rollingegg
 * @create_time 11/7/2020 11:12 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift implements Serializable {
    private DayOfWeek startDay;
    private DayOfWeek endDay;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * 对应的早班、晚班、全天三个班次
     */
    public static final Shift DAY_SHIFT=new Shift(DayOfWeek.MONDAY,DayOfWeek.FRIDAY,LocalTime.of(7,0),LocalTime.of(19,0));
    public static final Shift NIGHT_SHIFT=new Shift(DayOfWeek.MONDAY,DayOfWeek.SATURDAY,LocalTime.of(19,0),LocalTime.of(7,0));
    public static final Shift ALL_TIME_SHIFT=new Shift(DayOfWeek.MONDAY,DayOfWeek.MONDAY,LocalTime.of(0,0),LocalTime.of(23,59));

    public static boolean shiftTimeslotConflict(Shift shift, Timeslot timeslot) {
        if (null == shift || null == timeslot) {
            return false;
        }
        if (ALL_TIME_SHIFT.equals(shift)) {
            return false;
        } else {
            LocalTime startTime = timeslot.getStartDateTime().toLocalTime();
            LocalTime endTime = timeslot.getEndDateTime().toLocalTime();
            DayOfWeek dayOfWeek = timeslot.getStartDateTime().getDayOfWeek();
            if (shift.startDay.compareTo(dayOfWeek) > 0 || shift.endDay.compareTo(dayOfWeek) < 0) {
                return true;
            }
            if (DAY_SHIFT.equals(shift)) {
                if (startTime.compareTo(endTime) > 0) {
                    // 23：00-次日0：00
                    return true;
                }
                // 7:00之前开始或者19:00之后结束均为冲突
                return startTime.compareTo(shift.startTime) < 0 || endTime.compareTo(shift.endTime) > 0;
            } else {
                // 每个时间段都是一小时，只要保证19:00之后开始或者7:00之前结束
                return !(startTime.compareTo(shift.startTime) >= 0 || endTime.compareTo(shift.endTime) <= 0);
            }
        }
    }
}
