package com.webplusgd.aps.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("toby")
@SpringBootTest
@Slf4j
class APSControllerTest {
    @Autowired
    private APSController apsController;
    @Autowired
    private ChartController chartController;
    private MockMvc mockMvc;
    private MockMvc mockMvc2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apsController).build();
        mockMvc2 = MockMvcBuilders.standaloneSetup(chartController).build();
    }

    @Test
    void startAps() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/aps/startAps")
                .param("currentDate", "2018-10-10 07:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
        MvcResult mvcResult2 = mockMvc2.perform(MockMvcRequestBuilders.get("/api/chart//getResourceLoadChart")
                .param("startDate", "2018-10-11"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult2.getResponse().getContentAsString());
    }
}