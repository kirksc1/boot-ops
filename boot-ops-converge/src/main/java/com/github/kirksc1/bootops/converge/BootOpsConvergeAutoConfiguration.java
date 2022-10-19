package com.github.kirksc1.bootops.converge;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemManifestParser;
import com.github.kirksc1.bootops.core.ItemManifestReader;
import com.github.kirksc1.bootops.core.init.ItemInitializationExecutor;
import com.github.kirksc1.bootops.validate.ItemValidationExecutor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Predicate;

/**
 * ConvergeConfiguration is the Spring Boot Configuration class for the Boot Ops Converge functionality.
 */
@Configuration
public class BootOpsConvergeAutoConfiguration {

    /**
     * Default Converge command bean.
     */
    @Bean
    public ConvergeItemStreamCommand convergeItemStreamCommand(
            ItemManifestReader reader,
            ItemManifestParser parser,
            List<Predicate<Item>> filters,
            ApplicationEventPublisher publisher,
            ItemInitializationExecutor initializationExecutor,
            ItemValidationExecutor validationExecutor,
            ItemConvergeExecutor convergeExecutor) {
        return new ConvergeItemStreamCommand(reader, parser, filters, publisher, initializationExecutor, validationExecutor, convergeExecutor);
    }

    /**
     * Command to orchestrate the converge process.
     */
    @Bean
    public ItemConvergeExecutor itemConvergeExecutor(ApplicationEventPublisher eventPublisher) {
        return new ItemConvergeExecutor(eventPublisher);
    }

}
