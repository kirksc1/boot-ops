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
            Assertions.assertThat(context).hasSingleBean(ValidateItemStreamCommand.class);
        });
    }
}