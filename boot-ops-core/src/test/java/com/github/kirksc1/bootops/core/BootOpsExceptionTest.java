package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BootOpsExceptionTest {

    @Test
    public void testConstructor_whenOnlyMessage_thenMessageGettable() {
        BootOpsException exception = new BootOpsException("test");

        assertEquals("test", exception.getMessage());
    }

    @Test
    public void testConstructor_whenMessageAndThrowable_thenInfoGettable() {
        Throwable t = new RuntimeException("Forced Failure");
        BootOpsException exception = new BootOpsException("test", t);

        assertEquals("test", exception.getMessage());
        assertSame(t, exception.getCause());
    }

    @Test
    public void testConstructor_whenNullMessageAndNullThrowable_thenNullsGettable() {
        BootOpsException exception = new BootOpsException(null, null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}