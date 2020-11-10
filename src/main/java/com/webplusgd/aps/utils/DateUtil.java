package com.webplusgd.aps.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Rollingegg
 * @create_time 11/10/2020 12:11 AM
 */
public class DateUtil {
    public static LocalDateTime date2LocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static boolean isAfter(LocalDateTime date1, Date date2) {
        return date1.isAfter(date2LocalDateTime(date2));
    }

    public static long daysDiff(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return Duration.between(dateTime1, dateTime2).toDays();
    }

    public static long hoursDiff(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return Duration.between(dateTime1, dateTime2).toHours();
    }

    public static Date localDateTime2Date(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
