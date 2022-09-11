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