package com.github.kirksc1.bootops.cli;

import com.github.kirksc1.bootops.core.ItemManifestStreamFactory;
import com.github.kirksc1.bootops.core.ItemStreamCommandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BootOpsApplicationRunnerTest {

    private static ItemStreamCommandService service = mock(ItemStreamCommandService.class);
    private static ItemManifestStreamFactory factory = mock(ItemManifestStreamFactory.class);

    @BeforeEach
    public void beforeEach() {
        reset(service, factory);
    }

    @Test
    public void testConstructor_whenNullService_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BootOpsApplicationRunner(null, factory);
        });

        assertEquals("The ItemStreamCommandService provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullFactory_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BootOpsApplicationRunner(service, null);
        });

        assertEquals("The ItemManifestStreamFactory provided was null", thrown.getMessage());
    }

    @Test
    public void testRun_whenNoCommandProvided_thenThrowIllegalStateException() {
        BootOpsApplicationRunner runner = new BootOpsApplicationRunner(service, factory);
        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> {
            runner.run(new DefaultApplicationArguments());
        });

        assertEquals("No command provided", thrown.getMessage());
    }

    @Test
    public void testRun_whenOneCommandProvided_thenCallServiceForCommandWithParameters() throws Exception {
        BootOpsApplicationRunner runner = new BootOpsApplicationRunner(service, factory);

        DefaultApplicationArguments args = new DefaultApplicationArguments("--command=test", "--log=info");

        runner.run(args);

        ArgumentCaptor<Map<String,List<String>>> paramsCaptor = ArgumentCaptor.forClass(Map.class);

        verify(service, times(1)).execute(eq("test"), paramsCaptor.capture(), same(factory));

        Map<String,List<String>> params = paramsCaptor.getValue();

        assertEquals(2, params.size());
        assertEquals("test", params.get("command").get(0));
        assertEquals("info", params.get("log").get(0));
    }

    @Test
    public void testRun_whenTwoCommandsProvided_thenCallServiceForEachCommandWithParameters() throws Exception {
        BootOpsApplicationRunner runner = new BootOpsApplicationRunner(service, factory);

        DefaultApplicationArguments args = new DefaultApplicationArguments("--command=test", "--command=test2", "--log=info");

        runner.run(args);

        ArgumentCaptor<Map<String, List<String>>> paramsCaptor = ArgumentCaptor.forClass(Map.class);

        verify(service, times(1)).execute(eq("test"), paramsCaptor.capture(), same(factory));
        verify(service, times(1)).execute(eq("test2"), paramsCaptor.capture(), same(factory));

        Map<String,List<String>> params = paramsCaptor.getValue();

        assertEquals(2, params.size());

        assertEquals(2, params.get("command").size());
        assertEquals("test", params.get("command").get(0));
        assertEquals("test2", params.get("command").get(1));

        assertEquals(1, params.get("log").size());
        assertEquals("info", params.get("log").get(0));
    }
}