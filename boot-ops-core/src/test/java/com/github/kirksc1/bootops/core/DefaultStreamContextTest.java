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

class DefaultStreamContextTest {

    @Test
    public void testPut_whenKeyNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DefaultStreamContext().put(null, "val");
        });

        Assertions.assertEquals("The key provided was null", thrown.getMessage());
    }

    @Test
    public void testPut_whenKeyObjectAdded_thenObjectGettable() {
        DefaultStreamContext context = new DefaultStreamContext();
        context.put("key", "value");

        assertSame("value", context.get("key"));
    }

    @Test
    public void testRemove_whenKeyNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DefaultStreamContext().remove(null);
        });

        Assertions.assertEquals("The key provided was null", thrown.getMessage());
    }

    @Test
    public void testRemove_whenKeyPresent_thenReturnObject() {
        DefaultStreamContext context = new DefaultStreamContext();
        context.put("key", "value");

        assertSame("value", context.remove("key"));
    }

    @Test
    public void testRemove_whenKeyNotPresent_thenReturnNull() {
        DefaultStreamContext context = new DefaultStreamContext();

        assertNull(context.remove("key"));
    }

    @Test
    public void testRemove_whenKeyRemoved_thenObjectNotGettable() {
        String key = "key";
        DefaultStreamContext context = new DefaultStreamContext();
        context.put(key, "value");

        context.remove(key);

        assertNull(context.get(key));
    }

    @Test
    public void testGet_whenKeyNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DefaultStreamContext().get(null);
        });

        Assertions.assertEquals("The key provided was null", thrown.getMessage());
    }

    @Test
    public void testGet_whenKeyPresent_thenReturnObject() {
        DefaultStreamContext context = new DefaultStreamContext();
        context.put("key", "value");

        assertSame("value", context.get("key"));
    }

    @Test
    public void testGet_whenKeyNotPresent_thenReturnNull() {
        DefaultStreamContext context = new DefaultStreamContext();

        assertNull(context.get("key"));
    }

    @Test
    public void testkeySet_whenNoObjects_thenReturnEmptySet() {
        DefaultStreamContext context = new DefaultStreamContext();

        assertTrue(context.keySet().isEmpty());
    }

    @Test
    public void testKeySet_whenKeyObjectAdded_thenKeySetContainsKey() {
        DefaultStreamContext context = new DefaultStreamContext();
        context.put("key", "value");

        assertEquals(1, context.keySet().size());
        assertSame("key", context.keySet().toArray()[0]);
    }

    @Test
    public void testContainsKey_whenKeyPresent_thenReturnTrue() {
        DefaultStreamContext context = new DefaultStreamContext();
        context.put("key", "value");

        assertTrue(context.containsKey("key"));
    }

    @Test
    public void testContainsKey_whenKeyAbsent_thenReturnFalse() {
        DefaultStreamContext context = new DefaultStreamContext();

        assertFalse(context.containsKey("key"));
    }
}