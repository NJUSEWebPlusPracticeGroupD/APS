package com.webplusgd.aps.optaplanner;

import optaplanner.ApsSolution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("steve")
@SpringBootTest
class DataManagerTest {

    @Autowired
    public DataManager dataManager;

    @Test
    void generateProblem() {
        ApsSolution problem=dataManager.generateProblem(LocalDateTime.of(2018,11,1,0,0));
        assertEquals(24*58,problem.getTimeslotList().size());
    }
}