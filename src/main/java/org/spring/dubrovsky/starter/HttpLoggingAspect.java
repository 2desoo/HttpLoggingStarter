package org.spring.dubrovsky.starter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class HttpLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingAspect.class);

    private final HttpLoggingProperties properties;

    public HttpLoggingAspect(HttpLoggingProperties properties) {
        this.properties = properties;
    }

    @Before("@annotation(LoggingExecution)")
    public void logBefore(JoinPoint joinPoint) {
        if (properties.isEnabled() && properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
            logger.info("Method {} is about to execute", joinPoint.getSignature().getName());
        }
    }

    @AfterThrowing(pointcut = "@annotation(LoggingException)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        if (properties.isEnabled()) {
            logger.error("Exception in method {}: {}", joinPoint.getSignature().getName(), exception.getMessage());
        }
    }

    @AfterReturning(pointcut = "@annotation(LoggingReturn)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (properties.isEnabled()) {
            logger.info("Method {} executed successfully: {}", joinPoint.getSignature().getName(), result);
        }
    }

    @Around("@annotation(LoggingAround)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        Object result;
        try {
            if (properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
                logger.info("Method {} execution started", joinPoint.getSignature().getName());
            }
            result = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("Error while executing method {}: {}", joinPoint.getSignature().getName(), t.getMessage());
            throw t;
        }
        if (properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
            logger.info("Method {} completed successfully", joinPoint.getSignature().getName());
        }
        return result;
    }
}
