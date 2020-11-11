package com.webplusgd.aps.utils;

import java.time.*;
import java.util.Date;

/**
 * @author Rollingegg
 * @create_time 11/10/2020 12:11 AM
 */
public class DateUtil {
    public static LocalDateTime date2LocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static LocalDate date2LocalDate(Date date){
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean isAfter(LocalDateTime date1, Date date2) {
        return date1.isAfter(date2LocalDateTime(date2));
    }

    /**
     * 前者到后者的日期距离（晚于时负数）
     * @param dateTime1 前者
     * @param dateTime2 后者
     * @return
     */
    public static long daysDiff(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return Duration.between(dateTime1, dateTime2).toDays();
    }

    public static long hoursDiff(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return Duration.between(dateTime1, dateTime2).toHours();
    }

    public static Date localDateTime2Date(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }
}
