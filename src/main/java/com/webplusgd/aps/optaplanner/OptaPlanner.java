package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.Timeslot;
import com.webplusgd.aps.optaplanner.score.ApsScoreCalculator;
import com.webplusgd.aps.optaplanner.utils.DataUtil;
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
    @Autowired
    private DataManager dataManager;
    @Autowired
    private FCFSPlanner fcfsPlanner;
    private SolverManager<ApsSolution, UUID> solverManager;
    private SolverJob<ApsSolution, UUID> solverJob;
    private LocalDateTime currentScheduleTime;
    private ApsSolution problem;
    private ApsSolution solution;
    private static final Integer HOUR_UNIT_FOR_BL=1;
    private static final Integer DEFAULT_STEP=1;

    @PostConstruct
    public void init() throws IOException {
        SolverConfig solverConfig = SolverConfig.createFromXmlInputStream(new ClassPathResource("/optaplanner/solver/ApsConfig.xml").getInputStream());
        solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
        log.info("创建SolverManager");
    }

    /**
     * 排程计划，保存在内存中
     */
    private List<ScheduledTask> output;

    /**
     * 定时任务，异步求解排程计划
     */
    @Scheduled(fixedRate = 30000)
    private void fixedRateJob() {
        if (solverJob != null) {
            tryGetPlan();
            log.info("SolverJob's Status: {}", solverJob.getSolverStatus());
        }
    }

    /**
     * 加载求解器任务
     *
     * @param problem 问题集
     */
    public void scheduleInternal(ApsSolution problem) {
        initScheduleWithFIFOPlanner(problem);
        UUID problemId = UUID.randomUUID();
        solverJob = solverManager.solve(problemId, problem);
        log.info("初始化SolverJob");
    }

    @Override
    public List<ScheduledTask> getPlan(Timestamp currentTime) {
        startSchedule(DateUtil.getDateTimeOfTimestamp(currentTime.getTime()));
        return waitForPlan();
    }

    @Override
    public void startSchedule(LocalDateTime currentTime) {
        currentScheduleTime = currentTime;
        log.info("当前系统时间（排程启动时间）设置为：{}",currentTime);
        problem = dataManager.generateStandardProblem(currentScheduleTime);
        log.info("加载问题集");
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
            log.info("开启排程任务");
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
        List<Task> result= DataUtil.toResult(solution.getTaskList());
        Task task;
        Order order;
        for (int i = result.size() - 1; i >= 0; i--) {
            task = result.get(i);
            order = task.getOrder();
            if (order.getFinishTime() == null) {
                order.setFinishTime(task.getTimeslot().getEndDateTime());
            }
        }
        return result.stream().map(ScheduledTask::new).collect(Collectors.toList());
    }

    private void initScheduleWithFIFOPlanner(ApsSolution problem) {
        fcfsPlanner.scheduleInterval(problem);
        log.info("使用FIFO初始化问题集");
        List<Order> orderList = problem.getOrderList();
        for (Order order : orderList) {
            order.setFinishTime(null);
        }
    }
}
