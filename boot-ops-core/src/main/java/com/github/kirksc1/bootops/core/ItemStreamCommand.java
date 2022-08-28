package com.github.kirksc1.bootops.core;

import org.springframework.util.Assert;

import java.net.URI;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An ItemStreamCommand encapsulates a named command to be executed on a Stream of items referenced by a URI.
 */
public abstract class ItemStreamCommand {
    private final String name;

    /**
     * Construct a new instance with the provided name.
     * @param name The name of the command.
     * @throws IllegalArgumentException if the name provided is null.
     */
    protected ItemStreamCommand(String name) {
        Assert.notNull(name, "The name provided was null");
        Assert.hasText(name, "The name provided was empty");

        this.name = name;
    }

    /**
     * Execute the command on each of the items referenced by the provided URIs.
     * @param uriStream The stream of URIs referencing the items.
     * @param parameters A collection of command configuraton parameters.
     * @return True if everything executed successfully, otherwise false.
     */
    abstract boolean execute(Stream<URI> uriStream, Map<String,String> parameters);
}
