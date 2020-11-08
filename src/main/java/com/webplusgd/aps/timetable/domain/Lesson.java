package com.webplusgd.aps.timetable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * @author Rollingegg
 * @create_time 10/16/2020 10:22 AM
 */
@Data
@PlanningEntity
@Schema(name = "Lesson",description = "课程安排")
@NoArgsConstructor
public class Lesson {
    @Schema(example = "1",description = "课程id")
    private Long id;

    @Schema(example = "Math", description = "课程科目")
    private String subject;
    @Schema(example = "A. Turing", description = "任课老师")
    private String teacher;
    @Schema(example = "9th grade", description = "课程年级")
    private String studentGroup;

    @Schema
    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    private Timeslot timeslot;

    @Schema
    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    private Room room;

    public Lesson(Long id, String subject, String teacher, String studentGroup) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.studentGroup = studentGroup;
    }
    @Override
    public String toString() {
        return subject + "(" + id + ")";
    }
}
