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