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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.kirksc1.bootops.core.Item;

import java.io.IOException;

/**
 * ItemSerializer is an implementation of the Jackson Serializer that serializes data from an Item instance.
 */
public class ItemSerializer extends StdSerializer<Item> {

    /**
     * Construct a new instance.
     */
    public ItemSerializer() {
        super(Item.class);
    }

    /**
     * Serialize the provided Item into a text stream of JSON.
     * @param value The Item to be serialized.
     * @param gen The Jackson JSON generator.
     * @param provider The Jackson Serializer provider.
     * @throws IOException when an error occurs writing the serialized data.
     */
    @Override
    public void serialize(Item value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            gen.writeStartObject();
            gen.writeStringField("name", value.getName());
            gen.writeFieldName("attributes");
            gen.writeObject(value.getAttributes());
            gen.writeEndObject();
        }
    }
}
