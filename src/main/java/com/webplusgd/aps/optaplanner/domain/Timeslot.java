package com.webplusgd.aps.optaplanner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Rollingegg
 * @create_time 11/4/2020 6:33 PM
 * 排程的工作时间
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot implements Serializable {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public long getDurationOfHours(){
        return Duration.between(startDateTime,endDateTime).toHours();
    }
}
