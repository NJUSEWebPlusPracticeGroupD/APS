package com.webplusgd.aps.aspect;


import com.webplusgd.aps.properties.ApsProperties;
import com.webplusgd.aps.service.LogService;
import com.webplusgd.aps.utils.HttpContextUtil;
import com.webplusgd.aps.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author Rollingegg
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private final ApsProperties apsProperties;

    public LogAspect(ApsProperties apsProperties) {
        this.apsProperties = apsProperties;
    }

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.webplusgd.aps.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        // 获取 request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 获取 访问url与方法类型
        String uri = request.getRequestURI();
        String method = request.getMethod();
        // 设置 IP 地址
        String ip = IPUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (apsProperties.isOpenAopLog()) {
            // todo 保存日志
            log.info("IP:{} 访问 uri 为 [{}]，方法类型为 {} 的接口 用时为 {}", ip, uri, method, time);
        }
        return result;
    }
}
