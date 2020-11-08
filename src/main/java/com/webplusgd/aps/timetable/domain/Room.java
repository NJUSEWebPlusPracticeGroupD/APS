package com.webplusgd.aps.timetable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rollingegg
 * @create_time 10/16/2020 10:28 AM
 * 转化为Json对象需要无参构造
 */
@Data
@NoArgsConstructor
public class Room {
    @Schema(example = "Room A", description = "房间号")
    private String name;
    public Room(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
