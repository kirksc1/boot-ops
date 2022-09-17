package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttributeTypeTest {

    @Test
    public void testConstructor_whenNameNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AttributeType(null, String.class);
        });

        Assertions.assertEquals("The name provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenClassNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AttributeType("test", null);
        });

        Assertions.assertEquals("The class provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenDetailsProvided_thenNameAndClassGettable() {
        AttributeType type = new AttributeType("test", String.class);

        assertEquals("test", type.getName());
        assertSame(String.class, type.getAttributeClass());
    }

}