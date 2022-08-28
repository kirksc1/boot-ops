package com.github.kirksc1.bootops.core;

import java.io.OutputStream;
import java.text.ParseException;

/**
 * ItemManifestParser is a functional interface for parsing Item manifest configuration.
 */
@FunctionalInterface
public interface ItemManifestParser {

    /**
     * Parse the provided OutputStream into an Item.
     *
     * @param os The OutputStream to parse.
     * @return The parsed Item.
     * @throws ParseException if the data could not be parsed successfully into an Item.
     */
    Item parse(OutputStream os) throws ParseException;

}
