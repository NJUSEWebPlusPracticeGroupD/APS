package com.webplusgd.aps.timetable.solver;

import com.webplusgd.aps.timetable.domain.TimeTable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @author Rollingegg
 * @create_time 10/16/2020 10:45 AM
 * OptaPlanner的demo代码，课程安排
 */
@RestController
@RequestMapping("api/timeTable")
@Tag(name = "TimeTable", description = "the api for timetable")
public class TimeTableController {
    @Autowired
    private SolverManager<TimeTable, UUID> solverManager;

//    @PostConstruct
//    public void init() throws IOException {
//        SolverConfig solverConfig=SolverConfig.createFromXmlInputStream(new ClassPathResource("/optaplanner/timetable/TimeTableScoreConfig.xml").getInputStream());
//        solverManager=SolverManager.create(solverConfig,new SolverManagerConfig());
//    }

    @Operation(summary = "Solve TimeTable by Posting the original", tags = {"TimeTable"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TimeTable.class))})}
    )
    @PostMapping("/solve")
    public TimeTable solve(@RequestBody TimeTable problem) {
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<TimeTable, UUID> solverJob = solverManager.solve(problemId, problem);
        TimeTable solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }
}
