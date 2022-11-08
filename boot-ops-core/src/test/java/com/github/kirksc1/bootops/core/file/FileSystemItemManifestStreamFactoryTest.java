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
package com.github.kirksc1.bootops.core.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileSystemItemManifestStreamFactoryTest {

    @Test
    public void testConstructor_whenRootFileIsNull_thenThrowIllegalArgumentException() throws IOException {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new FileSystemItemManifestStreamFactory(null);
        });

        Assertions.assertEquals("The root directory File provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenRootFileIsNotADirectory_thenThrowIllegalArgumentException() throws IOException {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new FileSystemItemManifestStreamFactory(new File("src/test/resources/file/factory/root.yaml"));
        });

        Assertions.assertEquals("The root directory File provided was not a directory", thrown.getMessage());
    }

    @Test
    public void testBuildStream_whenRootHasNoFiles_thenReturnNoURIs() throws IOException {
        File emptyDir = new File("src/test/resources/file/factory/empty");
        if (!emptyDir.exists()) {
            if (!emptyDir.mkdirs()) {
                throw new IllegalStateException("Unable to create directory 'src/test/resources/file/factory/empty'");
            }
        }

        File root = new File("src/test/resources/file/factory/empty");
        FileSystemItemManifestStreamFactory fileSystemItemManifestStreamFactory = new FileSystemItemManifestStreamFactory(root);

        Stream<URI> stream = fileSystemItemManifestStreamFactory.buildStream();

        List<URI> uriList = stream.collect(Collectors.toList());

        assertEquals(0, uriList.size());
    }

    @Test
    public void testBuildStream_whenRootHasFiles_thenReturnURIsToFiles() throws IOException {
        File root = new File("src/test/resources/file/factory");
        FileSystemItemManifestStreamFactory fileSystemItemManifestStreamFactory = new FileSystemItemManifestStreamFactory(root);

        Stream<URI> stream = fileSystemItemManifestStreamFactory.buildStream();

        List<URI> uriList = stream.collect(Collectors.toList());

        assertEquals(3, uriList.size());
        Collections.sort(uriList);
        assertTrue(uriList.get(0).toString().endsWith("factory/child/child.yaml"));
        assertTrue(uriList.get(1).toString().endsWith("factory/child/grandchild/grandchild.yaml"));
        assertTrue(uriList.get(2).toString().endsWith("factory/root.yaml"));
    }

}