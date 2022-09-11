package com.github.kirksc1.bootops.core.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
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
        assertTrue(uriList.get(0).toString().endsWith("factory/child/child.yaml"));
        assertTrue(uriList.get(1).toString().endsWith("factory/child/grandchild/grandchild.yaml"));
        assertTrue(uriList.get(2).toString().endsWith("factory/root.yaml"));
    }

}