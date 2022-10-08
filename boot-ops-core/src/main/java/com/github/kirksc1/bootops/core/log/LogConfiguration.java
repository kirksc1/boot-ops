package com.github.kirksc1.bootops.core.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * LogConfiguration is the Spring Boot Configuration class for the Boot Ops Core Log functionality.
 */
@ConditionalOnProperty(prefix="boot-ops.event.logging", name="enabled", havingValue="true", matchIfMissing = false)
public class LogConfiguration {

    /**
     * Default event logging bean.
     */
    @Bean
    public LoggingEventListener loggingEventListener() {
        return new LoggingEventListener();
    }
}
