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

import java.io.IOException;
import java.net.URI;
import java.util.stream.Stream;

/**
 * An ItemManifestStreamFactory encapsulates the functionality to create a stream of URIs that reference
 * Items.
 */
public interface ItemManifestStreamFactory {

    /**
     * Construct a stream of URIs referencing items.
     * @return A stream of URIs referencing the items.
     * @throws IOException if the stream could not be built.
     */
    Stream<URI> buildStream() throws IOException;
}
