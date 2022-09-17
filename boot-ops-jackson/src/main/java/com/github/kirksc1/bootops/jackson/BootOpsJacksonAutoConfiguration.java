package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.kirksc1.bootops.core.AttributeType;
import com.github.kirksc1.bootops.core.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ObjectMapper objectMapper(YAMLFactory yamlFactory, Optional<List<AttributeType>> optAttributeTypes) {
        ObjectMapper retVal = new ObjectMapper(yamlFactory);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Item.class, new ItemDeserializer(optAttributeTypes.orElse(new ArrayList<>())));
        retVal.registerModule(module);

        return retVal;
    }

    /**
     * Default JacksonItemManifestParser bean.
     */
    @Bean
    public JacksonItemManifestParser jacksonItemManifestParser(ObjectMapper objectMapper) {
        return new JacksonItemManifestParser(objectMapper);
    }
}
