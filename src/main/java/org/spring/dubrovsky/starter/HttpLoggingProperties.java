package org.spring.dubrovsky.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {

    /**
     * Включение/отключение логирования.
     */
    private boolean enabled = true;

    /**
     * Уровень детализации логов: BASIC, FULL.
     */
    private LogLevel level = LogLevel.BASIC;

    public enum LogLevel {
        BASIC, FULL
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }
}
