package com.github.kirksc1.bootops.cli;

import com.github.kirksc1.bootops.core.ItemManifestStreamFactory;
import com.github.kirksc1.bootops.core.ItemStreamCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@SpringBootTest(args = {"--command=any"})
@Import(BootOpsApplicationRunnerIntegrationTest.TestConfig.class)
class BootOpsApplicationRunnerIntegrationTest {

    @SpyBean
    BootOpsApplicationRunner bootOpsApplicationRunner;

    private static ItemStreamCommandService service = mock(ItemStreamCommandService.class);
    private static ItemManifestStreamFactory factory = mock(ItemManifestStreamFactory.class);

    @BeforeEach
    public void beforeEach() {
        reset(service, factory);
    }

    @Test
    void whenContextLoads_thenRunnersRun() throws Exception {
        Mockito.verify(bootOpsApplicationRunner, Mockito.times(1)).run(ArgumentMatchers.any());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ItemStreamCommandService testService() {
            return service;
        }

        @Bean
        @Primary
        public ItemManifestStreamFactory testFactory() {
            return factory;
        }
    }

}