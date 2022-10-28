/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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