package com.github.kirksc1.bootops.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * ItemManifestParser is a functional interface for parsing Item manifest configuration.
 */
@FunctionalInterface
public interface ItemManifestParser {

    /**
     * Parse the provided InputStream into an Item.
     *
     * @param inputStream The InputStream to parse.
     * @return The parsed Item.
     * @Throws IOException if the data could not be read successfully.
     * @throws ParseException if the data could not be parsed successfully into an Item.
     */
    Item parse(InputStream inputStream) throws IOException, ParseException;

}
