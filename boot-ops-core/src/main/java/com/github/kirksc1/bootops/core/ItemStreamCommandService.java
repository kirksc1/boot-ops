package com.github.kirksc1.bootops.core;

import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The ItemStreamCommandService provides an injectable service layer that locates and delegates command processing
 * on Item Streams.
 */
public class ItemStreamCommandService {

    private final Map<String,ItemStreamCommand> streamCommands = new HashMap<>();

    /**
     * Construct a new instance with the provided List of ItemStreamCommands.
     * @param streamCommands The ItemStreamCommands available for the service to execute.
     */
    public ItemStreamCommandService(List<ItemStreamCommand> streamCommands) {
        Assert.notNull(streamCommands, "The List of named ItemStreamCommands provided was null");

        streamCommands
                .forEach(itemStreamCommand -> this.streamCommands.put(itemStreamCommand.getName(), itemStreamCommand));
    }

    /**
     * Execute the command on the URI stream built by the provided ItemManifestStreamFactory.
     * @param commandName The name of the command to execute.
     * @param commandParams The parameters to govern the execution of the command.
     * @param streamFactory The ItemManifestStreamFactory to provide the stream of Item manifests.
     * @return An ItemStreamCommandResult to describe the result of the execution.
     */
    public ItemStreamCommandResult execute(String commandName, Map<String,List<String>> commandParams, ItemManifestStreamFactory streamFactory) {
        Assert.notNull(commandName, "The command name provided was null");
        Assert.notNull(commandParams, "The command parameters Map provided was null");
        Assert.notNull(streamFactory, "The ItemManifestStreamFactory provided was null");

        ItemStreamCommand command = Optional.ofNullable(streamCommands.get(commandName))
                .orElseThrow(() -> new BootOpsException("Command named '" + commandName +"' was not recognized"));

        Stream<URI> uriStream = null;
        try {
            uriStream = Optional.ofNullable(streamFactory.buildStream())
                    .orElseThrow(() -> new BootOpsException("ItemManifestStreamFactory produced a null stream"));
        } catch (IOException e) {
            throw new BootOpsException("Unable to build Stream<URI>", e);
        }

        return command.execute(uriStream, commandParams, new DefaultStreamContext());
    }
}
