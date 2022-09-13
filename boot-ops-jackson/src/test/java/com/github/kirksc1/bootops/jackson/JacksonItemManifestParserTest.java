package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JacksonItemManifestParserTest {

    @Test
    public void testConstructor_whenObjectMapperNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new JacksonItemManifestParser(null);
        });

        Assertions.assertEquals("The ObjectMapper provided was null", thrown.getMessage());
    }

    @Test
    public void testParse_whenInputStreamNull_thenThrowIllegalArgumentException() {
        JacksonItemManifestParser parser = new JacksonItemManifestParser(new ObjectMapper());
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(null);
        });

        Assertions.assertEquals("The InputStream provided was null", thrown.getMessage());
    }

    @Test
    public void testParse_whenInvalidInputStream_thenThrowParseException() {
        JacksonItemManifestParser parser = new JacksonItemManifestParser(new ObjectMapper(new YAMLFactory()));
        ParseException thrown = Assertions.assertThrows(ParseException.class, () -> {
            parser.parse(new FileInputStream(new File("src/test/resources/invalid-item.yaml")));
        });

        Assertions.assertEquals("Unable to parse Item", thrown.getMessage());
    }

    @Test
    public void testParse_whenValidInputStreamProvided_thenReturnItem() throws IOException {
        JacksonItemManifestParser parser = new JacksonItemManifestParser(new ObjectMapper(new YAMLFactory()));

        Item item = parser.parse(new FileInputStream(new File("src/test/resources/valid-item.yaml")));

        assertEquals("test", item.getName());
        assertEquals("value", item.getAttributes().get("attr"));
    }

}