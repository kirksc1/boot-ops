package com.github.kirksc1.bootops.core;

import com.github.kirksc1.bootops.core.file.FileSystemItemManifestReader;
import com.github.kirksc1.bootops.core.file.FileSystemItemManifestStreamFactory;
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
@Import({LogConfiguration.class})
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
