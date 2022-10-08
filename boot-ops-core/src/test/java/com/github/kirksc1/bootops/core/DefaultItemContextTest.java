package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultItemContextTest {

    @Test
    public void testPut_whenKeyNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DefaultItemContext().put(null, "val");
        });

        Assertions.assertEquals("The key provided was null", thrown.getMessage());
    }

    @Test
    public void testPut_whenKeyObjectAdded_thenObjectGettable() {
        DefaultItemContext context = new DefaultItemContext();
        context.put("key", "value");

        assertSame("value", context.get("key"));
    }

    @Test
    public void testRemove_whenKeyNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DefaultItemContext().remove(null);
        });

        Assertions.assertEquals("The key provided was null", thrown.getMessage());
    }

    @Test
    public void testRemove_whenKeyPresent_thenReturnObject() {
        DefaultItemContext context = new DefaultItemContext();
        context.put("key", "value");

        assertSame("value", context.remove("key"));
    }

    @Test
    public void testRemove_whenKeyNotPresent_thenReturnNull() {
        DefaultItemContext context = new DefaultItemContext();

        assertNull(context.remove("key"));
    }

    @Test
    public void testRemove_whenKeyRemoved_thenObjectNotGettable() {
        String key = "key";
        DefaultItemContext context = new DefaultItemContext();
        context.put(key, "value");

        context.remove(key);

        assertNull(context.get(key));
    }

    @Test
    public void testGet_whenKeyNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DefaultItemContext().get(null);
        });

        Assertions.assertEquals("The key provided was null", thrown.getMessage());
    }

    @Test
    public void testGet_whenKeyPresent_thenReturnObject() {
        DefaultItemContext context = new DefaultItemContext();
        context.put("key", "value");

        assertSame("value", context.get("key"));
    }

    @Test
    public void testGet_whenKeyNotPresent_thenReturnNull() {
        DefaultItemContext context = new DefaultItemContext();

        assertNull(context.get("key"));
    }

    @Test
    public void testkeySet_whenNoObjects_thenReturnEmptySet() {
        DefaultItemContext context = new DefaultItemContext();

        assertTrue(context.keySet().isEmpty());
    }

    @Test
    public void testKeySet_whenKeyObjectAdded_thenKeySetContainsKey() {
        DefaultItemContext context = new DefaultItemContext();
        context.put("key", "value");

        assertEquals(1, context.keySet().size());
        assertSame("key", context.keySet().toArray()[0]);
    }

    @Test
    public void testContainsKey_whenKeyPresent_thenReturnTrue() {
        DefaultItemContext context = new DefaultItemContext();
        context.put("key", "value");

        assertTrue(context.containsKey("key"));
    }

    @Test
    public void testContainsKey_whenKeyAbsent_thenReturnFalse() {
        DefaultItemContext context = new DefaultItemContext();

        assertFalse(context.containsKey("key"));
    }
}