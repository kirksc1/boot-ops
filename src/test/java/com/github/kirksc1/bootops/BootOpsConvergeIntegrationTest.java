package com.github.kirksc1.bootops;

import com.github.kirksc1.bootops.cli.BootOpsConsoleApplication;
import com.github.kirksc1.bootops.core.AttributeType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.io.Serializable;
import java.util.List;

@SpringBootTest(
        classes = {BootOpsConsoleApplication.class},
        args = {"--command=converge"},
        properties = {
                "logging.level.com.github.kirksc1=DEBUG",
                "boot-ops.event.logging.enabled=true",
                "boot-ops.manifest-root-directory=src/test/resources"
        })
@Import(BootOpsConvergeIntegrationTest.TestConfig.class)
public class BootOpsConvergeIntegrationTest {

    @Test
    public void testBootOps_whenSingleManifest_thenProcessOneItem() {

    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public AttributeType testAttributeType() {
            return new AttributeType("test", TestAttribute.class);
        }
    }

    static class TestAttribute implements Serializable {
        private String color;
        private int count;
        private boolean active;
        private List<String> types;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

}
