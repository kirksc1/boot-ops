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
     * Default ItemSerializer bean.
     */
    @Bean
    public ItemSerializer itemSerializer() {
        return new ItemSerializer();
    }

    /**
     * Default ItemDeserializer bean.
     */
    @Bean
    public ItemDeserializer itemDeserializer(Optional<List<AttributeType>> optAttributeTypes) {
        return new ItemDeserializer(optAttributeTypes.orElse(new ArrayList<>()));
    }

    /**
     * Default ObjectMapper bean.
     */
    @Bean
    public ObjectMapper objectMapper(YAMLFactory yamlFactory, ItemSerializer itemSerializer, ItemDeserializer itemDeserializer) {
        ObjectMapper retVal = new ObjectMapper(yamlFactory);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Item.class, itemDeserializer);
        module.addSerializer(itemSerializer);
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
