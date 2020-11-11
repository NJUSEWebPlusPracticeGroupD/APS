package com.webplusgd.aps.optaplanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("steve")
@SpringBootTest
class FCFSPlannerTest {

    @Autowired
    public FCFSPlanner planner;

    @Test
    void getPlan() {
        List<ScheduledTask> plan=planner.getPlan(Timestamp.valueOf(LocalDateTime.of(2018,10,10,7,0)));
    }
}