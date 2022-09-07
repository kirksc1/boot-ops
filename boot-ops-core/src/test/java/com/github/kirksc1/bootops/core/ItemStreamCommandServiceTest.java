package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class ItemStreamCommandServiceTest {

    private ItemStreamCommand command1 = mock(ItemStreamCommand.class);
    private ItemManifestStreamFactory streamFactory = mock(ItemManifestStreamFactory.class);
    private ItemStreamCommandResult commandResult = mock(ItemStreamCommandResult.class);

    private Map<String,String> commandParams = new HashMap<>();
    private List<ItemStreamCommand> commandList = new ArrayList<>();

    @BeforeEach
    public void before() {
        reset(command1, streamFactory, commandResult);

        commandList.clear();
        commandParams.clear();
    }

    @Test
    public void testConstructor_whenNullCommandList_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemStreamCommandService(null);
        });

        Assertions.assertEquals("The List of named ItemStreamCommands provided was null", thrown.getMessage());
    }

    @Test
    public void testExecute_whenNullCommandName_thenThrowIllegalArgumentException() {
        ItemStreamCommandService service = new ItemStreamCommandService(commandList);

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.execute(null, commandParams, streamFactory);
        });

        Assertions.assertEquals("The command name provided was null", thrown.getMessage());
    }

    @Test
    public void testExecute_whenNullCommandParameters_thenThrowIllegalArgumentException() {
        ItemStreamCommandService service = new ItemStreamCommandService(commandList);

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.execute("test", null, streamFactory);
        });

        Assertions.assertEquals("The command parameters Map provided was null", thrown.getMessage());
    }

    @Test
    public void testExecute_whenNullItemManifestStreamFactory_thenThrowIllegalArgumentException() {
        ItemStreamCommandService service = new ItemStreamCommandService(commandList);

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.execute("test", commandParams, null);
        });

        Assertions.assertEquals("The ItemManifestStreamFactory provided was null", thrown.getMessage());
    }

    @Test
    public void testExecute_whenCommandNotFoundByName_thenThrowBootOpsException() {
        ItemStreamCommandService service = new ItemStreamCommandService(commandList);

        BootOpsException thrown = Assertions.assertThrows(BootOpsException.class, () -> {
            service.execute("test", commandParams, streamFactory);
        });

        Assertions.assertEquals("Command named 'test' was not recognized", thrown.getMessage());
    }

    @Test
    public void testExecute_whenItemManifestStreamFactoryReturnsNullStream_thenThrowBootOpsException() {
        when(streamFactory.buildStream()).thenReturn(null);
        when(command1.getName()).thenReturn("test");

        commandList.add(command1);
        ItemStreamCommandService service = new ItemStreamCommandService(commandList);

        BootOpsException thrown = Assertions.assertThrows(BootOpsException.class, () -> {
            service.execute("test", commandParams, streamFactory);
        });

        Assertions.assertEquals("ItemManifestStreamFactory produced a null stream", thrown.getMessage());
    }

    @Test
    public void testExecute_whenAllDataProvided_thenCommandExecutedAndResultReturned() {
        URI uri = URI.create("test://test.com");
        Stream<URI> uriStream = Arrays.stream(new URI[]{uri});
        when(streamFactory.buildStream()).thenReturn(uriStream);
        when(command1.getName()).thenReturn("test");
        when(command1.execute(same(uriStream), same(commandParams), any(StreamContext.class))).thenReturn(commandResult);

        commandList.add(command1);
        ItemStreamCommandService service = new ItemStreamCommandService(commandList);

        ItemStreamCommandResult result = service.execute("test", commandParams, streamFactory);

        assertSame(commandResult, result);

        verify(command1, times(1)).execute(same(uriStream), same(commandParams), any(StreamContext.class));
    }

}