/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
