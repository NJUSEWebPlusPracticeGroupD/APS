package com.webplusgd.aps.optaplanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@ActiveProfiles("toby")
@SpringBootTest
class OptaPlannerTest {

    @Autowired
    public OptaPlanner planner;

    @Test
    void getPlan() {
        planner.getPlan(Timestamp.valueOf(LocalDateTime.of(2018,10,10,7,0)));
    }

    @Test
    void waitForPlan(){
        planner.startSchedule(LocalDateTime.of(2018,10,10,7,0));
        List<ScheduledTask> plan=planner.waitForPlan();
    }
    @Test
    void tryGetPlan(){
        planner.startSchedule(LocalDateTime.of(2018,10,10,7,0));
//        List<ScheduledTask> plan=planner.tryGetPlan();
    }
}