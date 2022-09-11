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
