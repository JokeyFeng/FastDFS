package com.yiheni.fastdfs.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;

/**
 * Created by Chenjing on 2018/1/22.
 *
 * @author Chenjing
 */
@Component
@Aspect
public class LogRecordConfig {

    private static final Logger log = LoggerFactory.getLogger(LogRecordConfig.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.yiheni.fastdfs.rest.*.*(..))")
    public void log() {
    }


    @Before("log()")
    public void before(JoinPoint joinPoint) {
        startTime.set(Instant.now().toEpochMilli());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("\n请求url : " + request.getRequestURL().toString()
                + ", \n请求方式 : " + request.getMethod()
                + ", \nIP地址 : " + request.getRemoteAddr()
                + ", \n类方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
                //+ ", \n参数 : " + JSON.toJSONString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        log.info("\n响应结果 : " + JSON.toJSONString(ret) + ",\n耗时: " + (Instant.now().toEpochMilli() - startTime.get()) + " milliseconds}");
        startTime.remove();
    }
}
