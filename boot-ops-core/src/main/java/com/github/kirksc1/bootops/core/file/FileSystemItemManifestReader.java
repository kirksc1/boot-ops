package com.github.kirksc1.bootops.core.file;

import com.github.kirksc1.bootops.core.ItemManifestReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;

public class FileSystemItemManifestReader implements ItemManifestReader {
    @Override
    public InputStream read(URI uri) throws IOException {
        return new FileInputStream(Paths.get(uri).toFile());
    }
}
