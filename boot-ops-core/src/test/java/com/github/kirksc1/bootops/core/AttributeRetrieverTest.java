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
package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttributeRetrieverTest {

    @Test
    public void testConstructor_whenAttributeNameNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AttributeRetriever<String>(null, String.class);
        });

        Assertions.assertEquals("The attribute name provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenAttributeClassNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AttributeRetriever<String>("test", null);
        });

        Assertions.assertEquals("The attribute class provided was null", thrown.getMessage());
    }

    @Test
    public void testRetrieve_whenAttributeMissing_thenReturnEmpty() {
        AttributeRetriever<String> retriever = new AttributeRetriever<>("test", String.class);

        Item item = new Item();

        assertEquals(Boolean.FALSE, retriever.retrieve(item).isPresent());
    }

    @Test
    public void testRetrieve_whenAttributePresentButDifferentClass_thenReturnEmpty() {
        AttributeRetriever<String> retriever = new AttributeRetriever<>("test", String.class);

        Item item = new Item();
        item.getAttributes().put("test", new Object());

        assertEquals(Boolean.FALSE, retriever.retrieve(item).isPresent());
    }

    @Test
    public void testRetrieve_whenAttributePresentAndCorrectClass_thenReturnAttribute() {
        AttributeRetriever<String> retriever = new AttributeRetriever<>("test", String.class);

        Item item = new Item();
        item.getAttributes().put("test", "value");

        assertEquals(Boolean.TRUE, retriever.retrieve(item).isPresent());
        assertEquals("value", retriever.retrieve(item).get());
    }

}