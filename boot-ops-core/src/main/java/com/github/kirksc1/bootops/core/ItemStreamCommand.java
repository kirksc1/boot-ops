package com.github.kirksc1.bootops.core;

import lombok.Getter;
import org.springframework.util.Assert;

import java.net.URI;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An ItemStreamCommand encapsulates a named command to be executed on a Stream of items referenced by a URI.
 */
@Getter
public abstract class ItemStreamCommand {
    private final String name;

    /**
     * Construct a new instance with the provided name.
     * @param name The name of the command.
     * @throws IllegalArgumentException if the name provided is null or emptystring.
     */
    protected ItemStreamCommand(String name) {
        Assert.notNull(name, "The name provided was null");
        Assert.hasText(name, "The name provided was empty");

        this.name = name;
    }

    /**
     * Execute the command on each of the items referenced by the provided URIs.
     * @param uriStream The stream of URIs referencing the items.
     * @param parameters A collection of command configuration parameters.
     * @param context The contextual data for the Item stream.
     * @return ItemStreamCommandResult The result of command execution on the stream.
     */
    abstract ItemStreamCommandResult execute(Stream<URI> uriStream, Map<String,String> parameters, StreamContext context);
}
