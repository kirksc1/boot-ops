package com.github.kirksc1.bootops.core;

import com.github.kirksc1.bootops.core.init.ItemInitializationExecutor;
import com.github.kirksc1.bootops.core.log.LoggingEventListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class BootOpsCoreAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(BootOpsCoreAutoConfiguration.class));

    @Test
    public void testConfiguration_whenConfigured_thenAllBeansAddedToContext() {
        this.contextRunner.run((context) -> {
            Assertions.assertThat(context).hasSingleBean(ItemManifestReader.class);
            Assertions.assertThat(context).hasSingleBean(ItemManifestStreamFactory.class);
            Assertions.assertThat(context).hasSingleBean(ItemStreamCommandService.class);
        });
    }

    @Test
    public void testConfiguration_whenLoggingEnabled_thenAllLogBeansAddedToContext() {
        this.contextRunner
                .withPropertyValues("boot-ops.event.logging.enabled=true")
                .run((context) -> {
            Assertions.assertThat(context).hasSingleBean(LoggingEventListener.class);
        });
    }

    @Test
    public void testConfiguration_whenLoggingNotConfigured_thenNoLogBeansAddedToContext() {
        this.contextRunner
                .run((context) -> {
                    Assertions.assertThat(context).doesNotHaveBean(LoggingEventListener.class);
                });
    }

    @Test
    public void testConfiguration_whenConfigured_thenAllInitBeansAddedToContext() {
        this.contextRunner.run((context) -> {
            Assertions.assertThat(context).hasSingleBean(ItemInitializationExecutor.class);
        });
    }

}