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
