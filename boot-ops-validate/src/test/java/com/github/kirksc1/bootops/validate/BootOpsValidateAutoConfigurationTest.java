package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.BootOpsCoreAutoConfiguration;
import com.github.kirksc1.bootops.jackson.BootOpsJacksonAutoConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class BootOpsValidateAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(BootOpsCoreAutoConfiguration.class))
            .withConfiguration(AutoConfigurations.of(BootOpsJacksonAutoConfiguration.class))
            .withConfiguration(AutoConfigurations.of(BootOpsValidateAutoConfiguration.class));

    @Test
    public void testConfiguration_whenConfigured_thenAllBeansAddedToContext() {
        this.contextRunner.run((context) -> {
            Assertions.assertThat(context).hasSingleBean(ItemValidationExecutor.class);
        });
    }
}