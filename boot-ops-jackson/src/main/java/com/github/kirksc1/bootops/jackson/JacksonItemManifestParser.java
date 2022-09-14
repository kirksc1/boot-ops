package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemManifestParser;
import com.github.kirksc1.bootops.core.ParseException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

public class JacksonItemManifestParser implements ItemManifestParser {

    private final ObjectMapper objectMapper;

    public JacksonItemManifestParser(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "The ObjectMapper provided was null");

        this.objectMapper = objectMapper;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Item parse(InputStream inputStream) throws IOException, ParseException {
        Assert.notNull(inputStream, "The InputStream provided was null");
        try {
            return objectMapper.readValue(inputStream, Item.class);
        } catch (JsonProcessingException e) {
            throw new ParseException("Unable to parse Item", e);
        }
    }
}
