package com.github.kirksc1.bootops.core;

import java.io.IOException;
import java.io.OutputStream;
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
     * @return The OutputStream of data read.
     * @throws IOException if the data located at the URI cannot be read.
     */
    OutputStream read(URI uri) throws IOException;

}
