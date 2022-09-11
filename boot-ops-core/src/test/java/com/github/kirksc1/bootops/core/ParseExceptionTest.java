package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParseExceptionTest {

    @Test
    public void testConstructor_whenOnlyMessage_thenMessageGettable() {
        ParseException exception = new ParseException("test");

        assertEquals("test", exception.getMessage());
    }

    @Test
    public void testConstructor_whenMessageAndThrowable_thenInfoGettable() {
        Throwable t = new RuntimeException("Forced Failure");
        ParseException exception = new ParseException("test", t);

        assertEquals("test", exception.getMessage());
        assertSame(t, exception.getCause());
    }

    @Test
    public void testConstructor_whenNullMessageAndNullThrowable_thenNullsGettable() {
        ParseException exception = new ParseException(null, null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}