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
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileSystemItemManifestReaderTest {

    @Test
    public void testRead_whenFileExists_thenInputStreamReturned() throws IOException {
        FileSystemItemManifestReader reader = new FileSystemItemManifestReader();

        InputStream inputStream = reader.read(new File("src/test/resources/file/reader/test.txt").toURI());
        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);

        String str = new String(data, Charset.defaultCharset());
        assertEquals("testing123", str);
    }

    @Test
    public void testRead_whenFileDoesNotExist_thenThrowIOException() {
        FileSystemItemManifestReader reader = new FileSystemItemManifestReader();

        IOException thrown = Assertions.assertThrows(IOException.class, () -> {
            reader.read(new File("src/test/resources/file/reader/missing.txt").toURI());
        });
    }

}