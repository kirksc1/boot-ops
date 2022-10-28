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
