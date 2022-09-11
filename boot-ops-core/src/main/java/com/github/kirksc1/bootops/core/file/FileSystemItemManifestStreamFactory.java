package com.github.kirksc1.bootops.core.file;

import com.github.kirksc1.bootops.core.ItemManifestStreamFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSystemItemManifestStreamFactory implements ItemManifestStreamFactory {

    private final File rootDirectory;

    public FileSystemItemManifestStreamFactory(File rootDirectory) {
        Assert.notNull(rootDirectory,"The root directory File provided was null");

        if (rootDirectory.isFile()) {
            throw new IllegalArgumentException("The root directory File provided was not a directory");
        }

        this.rootDirectory = rootDirectory;
    }

    @Override
    public Stream<URI> buildStream() throws IOException {
        return Files.walk(Paths.get(rootDirectory.toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toUri)
                .parallel();
    }
}
