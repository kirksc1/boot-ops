package com.github.kirksc1.bootops.cli;

import com.github.kirksc1.bootops.core.BootOpsCoreAutoConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class BootOpsCliAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(BootOpsCliAutoConfiguration.class));

    @Test
    public void testConfiguration_whenConfigured_thenAllBeansAddedToContext() {
        this.contextRunner
                .withUserConfiguration(BootOpsCoreAutoConfiguration.class)
                .run((context) -> {
            Assertions.assertThat(context).hasSingleBean(BootOpsApplicationRunner.class);
        });
    }

}