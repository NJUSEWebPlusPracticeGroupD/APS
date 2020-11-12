package com.webplusgd.aps.service.impl;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ResourceLoadServiceImplTest {

    @Test
    public void testDate(){
        LocalDateTime start=LocalDateTime.of(2020,11,10,7,0);
        LocalDateTime end=LocalDateTime.of(2020,11,17,4,0);
        assertEquals(6, Duration.between(start,end).toDays());
    }

    @Test
    public void testMap(){
        System.out.println(Stream.of(5,7,10,9,2,1,4).map(o->o*1.0/12).collect(Collectors.toList()));
        System.out.println(Stream.of(5,7,10,9,2,1,4).mapToInt(t->t).sum());
    }
}