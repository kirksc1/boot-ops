package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class BootOpsJacksonAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(BootOpsJacksonAutoConfiguration.class));

    @Test
    public void testConfiguration_whenConfigured_thenAllBeansAddedToContext() {
        this.contextRunner.run((context) -> {
            Assertions.assertThat(context).hasSingleBean(YAMLFactory.class);
            Assertions.assertThat(context).hasSingleBean(ItemDeserializer.class);
            Assertions.assertThat(context).hasSingleBean(ItemSerializer.class);
            Assertions.assertThat(context).hasSingleBean(ObjectMapper.class);
            Assertions.assertThat(context).hasSingleBean(JacksonItemManifestParser.class);
        });
    }

}