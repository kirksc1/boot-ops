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

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    public void testSetName_whenNameSet_thenNameGettable() {
        Item item = new Item();
        item.setName("test");

        assertEquals("test", item.getName());
    }

    @Test
    public void testSetUri_whenUriSet_thenUriGettable() {
        Item item = new Item();
        URI uri = URI.create("http://www.test.com");

        item.setUri(uri);

        assertSame(uri, item.getUri());
    }

    @Test
    public void testGetAttributes_whenCalled_thenAttributesGettable() {
        Item item = new Item();

        assertNotNull(item.getAttributes());
    }

    @Test
    public void testGetAttributes_whenCalled_thenAttributesEmpty() {
        Item item = new Item();

        assertEquals(0, item.getAttributes().size());
    }
}