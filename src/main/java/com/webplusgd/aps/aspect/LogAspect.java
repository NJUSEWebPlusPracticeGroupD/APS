package com.webplusgd.aps.aspect;


import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.properties.ApsProperties;
import com.webplusgd.aps.utils.HttpContextUtil;
import com.webplusgd.aps.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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


    @Pointcut("@annotation(com.webplusgd.aps.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;

        // 获取到方法签名
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Log aopLog = (Log) method.getAnnotation(Log.class);
        String comment = aopLog.value();

        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        // 获取 request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 获取 访问url与方法类型
        String uri = request.getRequestURI();
        String requestMethod = request.getMethod();
        String query = request.getQueryString();
        // 设置 IP 地址
        String ip = IPUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (apsProperties.isOpenAopLog()) {
            log.info("【{}】IP:{} 访问 uri 为 [{}] ，方法类型为 {} 的接口 用时为 {}，query为 {}，body为 {}", comment, ip, uri, requestMethod, time, query);
        }
        return result;
    }
}
