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
import java.io.InputStream;
import java.net.URI;

/**
 * ItemManifestReader is a functional interface for reading Item manifest configuration.
 */
@FunctionalInterface
public interface ItemManifestReader {

    /**
     * Read the manifest's data from the provided URI.
     *
     * @param uri The URI of the manifest to be read.
     * @return The InputStream of data read.
     * @throws IOException if the data located at the URI cannot be read.
     */
    InputStream read(URI uri) throws IOException;

}
