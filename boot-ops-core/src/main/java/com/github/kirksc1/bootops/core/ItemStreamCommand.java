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

import lombok.Getter;
import org.springframework.util.Assert;

import java.net.URI;
import java.util.List;
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
    abstract ItemStreamCommandResult execute(Stream<URI> uriStream, Map<String, List<String>> parameters, StreamContext context);
}
