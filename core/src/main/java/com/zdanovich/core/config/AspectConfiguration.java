package com.zdanovich.core.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

@Configuration
@EnableAspectJAutoProxy
@Aspect
@Log4j2
public class AspectConfiguration {

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.stereotype.Controller *)")
    void pointcut() {
    }

    @Around(value = "pointcut()")
    Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("ENTER: {} WITH ARG[S] = {}", joinPoint.getSignature().toString().replaceFirst("^.*\\s", ""),
                    Arrays.toString(joinPoint.getArgs()));
        }
        Object result = joinPoint.proceed();
        if (log.isInfoEnabled()) {
            log.info("EXIT : {} WITH RESULT = {}", joinPoint.getSignature().toString().replaceFirst("^.*\\s", ""),
                    result);
        }
        return result;
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "error")
    void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("THROW: {} WITH CAUSE = {}", joinPoint.getSignature().toString().replaceFirst("^.*\\s", ""),
                error.getCause() != null ? error.getCause() : "Unknown");
    }
}
