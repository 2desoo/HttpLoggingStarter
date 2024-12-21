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
        if (properties.isEnabled()) {
            String message = properties.getLogMessageBefore()
                    .replace("{method}", joinPoint.getSignature().getName());
            logWithLevel(properties.getLogLevel(), message);
        }
    }

    @AfterThrowing(pointcut = "@annotation(LoggingException)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        if (properties.isEnabled()) {
            String message = String.format("Exception in method %s: %s",
                    joinPoint.getSignature().getName(), exception.getMessage());
            logWithLevel(properties.getExceptionLogLevel(), message);
        }
    }

    @AfterReturning(pointcut = "@annotation(LoggingReturn)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (properties.isEnabled()) {
            String message = properties.getLogMessageAfter()
                    .replace("{method}", joinPoint.getSignature().getName())
                    .replace("{result}", String.valueOf(result));
            logWithLevel(properties.getLogLevel(), message);
        }
    }

    @Around("@annotation(LoggingAround)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        String message = properties.getLogMessageBefore()
                .replace("{method}", joinPoint.getSignature().getName());
        logWithLevel(properties.getLogLevel(), message);

        Object result = joinPoint.proceed();

        message = properties.getLogMessageAfter()
                .replace("{method}", joinPoint.getSignature().getName())
                .replace("{result}", String.valueOf(result));
        logWithLevel(properties.getLogLevel(), message);

        return result;
    }

    private void logWithLevel(HttpLoggingProperties.LogLevel logLevel, String message) {
        switch (logLevel) {
            case DEBUG -> logger.debug(message);
            case INFO -> logger.info(message);
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
            default -> logger.info(message);
        }
    }
}