//package com.webplusgd.aps.optaplanner.score;
//
//import com.webplusgd.aps.optaplanner.ApsSolution;
//import com.webplusgd.aps.optaplanner.domain.*;
//import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
//import com.webplusgd.aps.optaplanner.domain.resource.MachineResource;
//import org.junit.jupiter.api.Test;
//import org.optaplanner.test.api.score.stream.ConstraintVerifier;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//class ApsConstraintProviderTest {
//    private ConstraintVerifier<ApsConstraintProvider, ApsSolution> constraintVerifier = ConstraintVerifier.build(new ApsConstraintProvider(), ApsSolution.class, Task.class);
//
//    @Test
//    void productMinimumCapacityWithOneOrder() {
//        GroupResource g1 = new GroupResource(2, "g1", Shift.DAY_SHIFT);
//        GroupResource g2 = new GroupResource(3, "g2", Shift.DAY_SHIFT);
//        List<GroupResource> groupResourceList = new ArrayList<>();
//        groupResourceList.add(g1);
//        groupResourceList.add(g2);
//        MachineResource m1 = new MachineResource(3, "m1", Shift.ALL_TIME_SHIFT);
//        List<MachineResource> machineResourceList = new ArrayList<>();
//        machineResourceList.add(m1);
//        Order o1 = new Order(1, new Product(1, groupResourceList, machineResourceList, 60, 6, 1), 100, LocalDateTime.of(2018, 10, 29, 0, 0), null, null);
//        Timeslot t1 = new Timeslot(LocalDateTime.of(2018, 10, 27, 8, 0), LocalDateTime.of(2018, 10, 27, 9, 0));
//        Task task1 = new Task(o1, g1, t1);
//        Task task2 = new Task(o1, g2, t1);
//        constraintVerifier.verifyThat(ApsConstraintProvider::productMinimumCapacity)
//                .given(task1, task2)
//                .penalizesBy(2);
//    }
//}