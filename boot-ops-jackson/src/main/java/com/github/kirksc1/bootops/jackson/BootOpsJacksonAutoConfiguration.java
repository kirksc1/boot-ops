package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BootOpsJacksonAutoConfiguration is the Spring Boot Auto Configuration class for the Boot Ops Jackson functionality.
 */
@Configuration
public class BootOpsJacksonAutoConfiguration {

    /**
     * Default YAMLFactory bean.
     */
    @Bean
    public YAMLFactory yamlFactory() {
        return new YAMLFactory();
    }

    /**
     * Default ObjectMapper bean.
     */
    @Bean
    public ObjectMapper objectMapper(YAMLFactory yamlFactory) {
        return new ObjectMapper(yamlFactory);
    }

    /**
     * Default JacksonItemManifestParser bean.
     */
    @Bean
    public JacksonItemManifestParser jacksonItemManifestParser(ObjectMapper objectMapper) {
        return new JacksonItemManifestParser(objectMapper);
    }
}
