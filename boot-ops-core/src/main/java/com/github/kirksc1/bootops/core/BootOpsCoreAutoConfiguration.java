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
package com.github.kirksc1.bootops.core;

import com.github.kirksc1.bootops.core.file.FileSystemItemManifestReader;
import com.github.kirksc1.bootops.core.file.FileSystemItemManifestStreamFactory;
import com.github.kirksc1.bootops.core.init.InitConfiguration;
import com.github.kirksc1.bootops.core.log.LogConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * BootOpsCoreAutoConfiguration is the Spring Boot Auto Configuration class for the Boot Ops Core functionality.
 */
@Configuration
@Import({InitConfiguration.class, LogConfiguration.class})
public class BootOpsCoreAutoConfiguration {

    /**
     * The default ItemManifestReader bean.
     */
    @Bean
    public ItemManifestReader itemManifestReader() {
        return new FileSystemItemManifestReader();
    }

    /**
     * The default ItemManifestStreamFactory bean.
     */
    @Bean
    public ItemManifestStreamFactory itemManifestStreamFactory(@Value("${boot-ops.manifest-root-directory:manifests}") String itemRootDirectory) {
        return new FileSystemItemManifestStreamFactory(new File(itemRootDirectory));
    }

    /**
     * The ItemStreamCommandService bean.
     */
    @Bean
    public ItemStreamCommandService itemStreamCommandService(Optional<List<ItemStreamCommand>> optionalItemStreamCommands) {
        return new ItemStreamCommandService(optionalItemStreamCommands.orElse(new ArrayList<>()));
    }

    /**
     * An event listener to log BootOpsExceptionEvents.
     */
    @Bean
    public LoggingBootOpsExceptionEventListener loggingBootOpsExceptionEventListener() {
        return new LoggingBootOpsExceptionEventListener();
    }

}
