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
package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemManifestParser;
import com.github.kirksc1.bootops.core.ItemManifestReader;
import com.github.kirksc1.bootops.core.init.ItemInitializationExecutor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Predicate;

/**
 * BootOpsValidateAutoConfiguration is the Spring Boot Auto Configuration class for the Boot Ops Validate functionality.
 */
public class BootOpsValidateAutoConfiguration {

    /**
     * Bean to manage the Item validation process.
     */
    @Bean
    public ItemValidationExecutor itemValidationExecutor(ApplicationEventPublisher eventPublisher) {
        return new ItemValidationExecutor(eventPublisher);
    }

    /**
     * Command to orchestrate the validation process.
     */
    @Bean
    public ValidateItemStreamCommand validateItemStreamCommand(ItemManifestReader reader, ItemManifestParser parser, List<Predicate<Item>> filters, ApplicationEventPublisher publisher, ItemInitializationExecutor initializationExecutor, ItemValidationExecutor validationExecutor) {
        return new ValidateItemStreamCommand(reader, parser, filters, publisher, initializationExecutor,validationExecutor);
    }

}
