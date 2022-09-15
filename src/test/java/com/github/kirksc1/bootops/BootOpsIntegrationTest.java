package com.github.kirksc1.bootops;

import com.github.kirksc1.bootops.cli.BootOpsConsoleApplication;
import com.github.kirksc1.bootops.core.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = {BootOpsConsoleApplication.class},
        args = {"--command=test"},
        properties = {
                "logging.level.com.github.kirksc1=DEBUG",
                "boot-ops.manifest-root-directory=src/test/resources"
        })
@Import(BootOpsIntegrationTest.TestConfig.class)
public class BootOpsIntegrationTest {

    @Autowired
    private TestCommand command;

    @Test
    public void testBootOps_whenSingleManifest_thenProcessOneItem() {
        assertEquals("my-item", command.item.getName());

        assertEquals(1, command.parameters.get("command").size());
        assertEquals("test", command.parameters.get("command").get(0));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public TestCommand testCommand(ItemManifestReader reader, ItemManifestParser parser, ApplicationEventPublisher publisher) {
            return new TestCommand(reader, parser, new ArrayList<>(), publisher);
        }
    }

    static class TestCommand extends BaseItemStreamCommand {
        private Item item = null;
        private Map<String, List<String>> parameters;

        public TestCommand(ItemManifestReader reader, ItemManifestParser parser, List<Predicate<Item>> filters, ApplicationEventPublisher publisher) {
            super("test", reader, parser, filters, publisher);
        }

        @Override
        protected ItemCommandResult execute(Item item, Map<String, List<String>> parameters, StreamContext context) {
            this.item = item;
            this.parameters = new HashMap<>(parameters);

            return super.execute(item, parameters, context);
        }
    }

}
