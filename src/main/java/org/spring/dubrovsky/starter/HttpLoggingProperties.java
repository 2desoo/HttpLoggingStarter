package org.spring.dubrovsky.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {

    private boolean enabled = true;
    private LoggingLevel level = LoggingLevel.BASIC;
    private LogLevel logLevel = LogLevel.DEBUG;
    private LogLevel exceptionLogLevel = LogLevel.ERROR;
    private String logMessageBefore = "Executing method: {method}";
    private String logMessageAfter = "Executed method: {method} with result: {result}";

    public enum LoggingLevel {
        BASIC, FULL
    }

    public enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LoggingLevel getLevel() {
        return level;
    }

    public void setLevel(LoggingLevel level) {
        this.level = level;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogLevel getExceptionLogLevel() {
        return exceptionLogLevel;
    }

    public void setExceptionLogLevel(LogLevel exceptionLogLevel) {
        this.exceptionLogLevel = exceptionLogLevel;
    }

    public String getLogMessageBefore() {
        return logMessageBefore;
    }

    public void setLogMessageBefore(String logMessageBefore) {
        this.logMessageBefore = logMessageBefore;
    }

    public String getLogMessageAfter() {
        return logMessageAfter;
    }

    public void setLogMessageAfter(String logMessageAfter) {
        this.logMessageAfter = logMessageAfter;
    }
}
