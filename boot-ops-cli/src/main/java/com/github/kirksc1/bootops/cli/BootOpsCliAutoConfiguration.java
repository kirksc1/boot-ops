package com.github.kirksc1.bootops.cli;

import com.github.kirksc1.bootops.core.ItemManifestStreamFactory;
import com.github.kirksc1.bootops.core.ItemStreamCommandService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BootOpsCliAutoConfiguration is the Spring Boot Auto Configuration class for the Boot Ops CLI functionality.
 */
@Configuration
public class BootOpsCliAutoConfiguration {

    /**
     * ApplicationRunner bean for the command line.
     */
    @Bean
    public BootOpsApplicationRunner bootsOpsApplicationRunner(ItemStreamCommandService service, ItemManifestStreamFactory factory) {
        return new BootOpsApplicationRunner(service, factory);
    }
}
