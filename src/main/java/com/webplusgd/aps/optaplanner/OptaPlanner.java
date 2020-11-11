package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Rollingegg
 */
@Component
@Slf4j
public class OptaPlanner implements Planner {
    private SolverManager<ApsSolution, UUID> solverManager;
    private LocalDateTime currentScheduleTime;
    private static final Integer HOUR_UNIT_FOR_BL=1;
    private static final Integer DEFAULT_STEP=1;

    @PostConstruct
    public void init() throws IOException {
        SolverConfig solverConfig = SolverConfig.createFromXmlInputStream(new ClassPathResource("/optaplanner/solver/ApsConfig.xml").getInputStream());
        solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
    }

    @Autowired
    private DataManager dataManager;

    private SolverJob<ApsSolution, UUID> solverJob;
    private ApsSolution problem;
    private ApsSolution solution;
    /**
     * 排程计划，保存在内存中
     */
    private List<ScheduledTask> output;

    /**
     * 定时任务，异步求解排程计划
     */
    @Scheduled(fixedRate = 3000)
    private void fixedRateJob() {
        if (solverJob != null) {
            tryGetPlan();
        }
    }

    /**
     * 加载求解器任务
     *
     * @param problem 问题集
     */
    public void scheduleInternal(ApsSolution problem) {
        UUID problemId = UUID.randomUUID();
        solverJob = solverManager.solve(problemId, problem);
    }

    @Override
    public List<ScheduledTask> getPlan(Timestamp currentTime) {
        startSchedule(DateUtil.getDateTimeOfTimestamp(currentTime.getTime()));
        return waitForPlan();
    }

    @Override
    public void startSchedule(LocalDateTime currentTime) {
        currentScheduleTime = currentTime;
        // 加载问题集
        problem = dataManager.generateProblem(currentScheduleTime);
        scheduleInternal(problem);
    }

    @Override
    public List<ScheduledTask> tryGetPlan() {
        // 已有结果
        if (output != null) {
            return output;
        }
        // 当前没有排程任务
        if (solverJob == null) {
            throw new RuntimeException("当前没有排程任务");
        }
        // 已经解析完成
        if (solverJob.getSolverStatus() == SolverStatus.NOT_SOLVING) {
            return waitForPlan();
        }
        return null;
    }

    @Override
    public List<ScheduledTask> waitForPlan() {
        try {
            // 这里要花费很多时间!!!
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Solver failed...... | {}", e.getMessage());
            throw new IllegalStateException("Solving failed.", e);
        }
        log.info("Solving Score: {}", solution.getScore());
        // 保存排程结果
        output = createPlanFromSolution(solution);
        return output;
    }

    /**
     * 根据排程结果生成排程计划
     *
     * @param solution 排程结果
     * @return 排程计划
     */
    private List<ScheduledTask> createPlanFromSolution(ApsSolution solution) {
        List<ScheduledTask> res = new ArrayList<>();
        List<Task> taskList = solution.getTaskList().stream().filter(Task::hasNull).collect(Collectors.toList());
        HashMap<Integer,List<Task>> groupByOrderTaskList=new HashMap<>();
        for (Task task:taskList){
            Integer orderId=task.getOrder().getOrderId();
            groupByOrderTaskList.putIfAbsent(orderId,new ArrayList<>());
            groupByOrderTaskList.get(orderId).add(task);
        }
        for(Map.Entry<Integer,List<Task>> taskEntry:groupByOrderTaskList.entrySet()){
            List<Task> orderedTaskList=taskEntry.getValue();
            orderedTaskList.sort(Comparator.comparing(t->t.getTimeslot().getStartDateTime()));
            Task firstSubTask=orderedTaskList.get(0);
            Task lastSubTask=orderedTaskList.get(orderedTaskList.size()-1);
            for (Task singleTask: orderedTaskList){
                singleTask.getOrder().setStartTime(firstSubTask.getTimeslot().getStartDateTime());
                singleTask.getOrder().setFinishTime(lastSubTask.getTimeslot().getStartDateTime().plusHours(lastSubTask.getRemainHours()));

                LocalDateTime singleStartDateTime=singleTask.getTimeslot().getStartDateTime();
                for (int i = 0; i < singleTask.getRemainHours(); i++) {
                    Timeslot newTimeslot=new Timeslot(singleStartDateTime, singleStartDateTime.plusHours(HOUR_UNIT_FOR_BL));
                    res.add(new ScheduledTask(singleTask.getOrder(),singleTask.getGroupResource(),newTimeslot,DEFAULT_STEP));
                    res.add(new ScheduledTask(singleTask.getOrder(),singleTask.getAnotherGroupResource(),newTimeslot,DEFAULT_STEP));
                    res.add(new ScheduledTask(singleTask.getOrder(),singleTask.getMachineResource(),newTimeslot,DEFAULT_STEP));
                }
            }
        }
        return res;
    }
}
