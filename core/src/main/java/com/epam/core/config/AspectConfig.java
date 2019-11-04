package com.epam.core.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile(value = "dev")
@EnableAspectJAutoProxy
@Aspect
@Slf4j
public class AspectConfig {

    /*@Pointcut("execution(public * *(..))")//execution(public * com.epam.core.*.*(..))
    void monitor() {
    }*/

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.stereotype.Controller *)")
    void pointcut() {
    }

    @Around(value = "pointcut()")
    Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("ENTER: {} WITH ARG[S] = {}", joinPoint.getSignature().toString().replaceFirst("^.*\\s", ""),
                    Arrays.toString(joinPoint.getArgs()));
        }
        Object result = joinPoint.proceed();
        if (log.isDebugEnabled()) {
            log.debug("EXIT : {} WITH RESULT = {}", joinPoint.getSignature().toString().replaceFirst("^.*\\s", ""),
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
