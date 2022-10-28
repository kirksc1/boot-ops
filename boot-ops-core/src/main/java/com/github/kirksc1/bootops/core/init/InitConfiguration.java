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
package com.github.kirksc1.bootops.core.init;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * InitConfiguration is the Spring Boot Configuration class for the Boot Ops Core Initialization functionality.
 */
@Configuration
public class InitConfiguration {

    /**
     * Bean to manage the Item initialization process.
     */
    @Bean
    public ItemInitializationExecutor itemInitializationExecutor(ApplicationEventPublisher eventPublisher) {
        return new ItemInitializationExecutor(eventPublisher);
    }

}
