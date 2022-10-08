package com.github.kirksc1.bootops.core.init;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * InitConfiguration is the Spring Boot Configuration class for the Boot Ops Core Initialization functionality.
 */
@Configuration
public class InitConfiguration {

    /**
     * Bean to manage the Item initialization process.
     */
    @Bean
    public ItemInitializationExecutor itemInitializationExecutor(ApplicationEventPublisher eventPublisher) {
        return new ItemInitializationExecutor(eventPublisher);
    }

}
