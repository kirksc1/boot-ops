package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

class BootOpsExceptionEventTest {

    private Item item = mock(Item.class);

    @BeforeEach
    public void beforeEach() {
        reset(item);
    }

    @Test
    public void testConstructor_whenBootOpsExceptionNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BootOpsExceptionEvent(this, null);
        });

        Assertions.assertEquals("The BootOpsException provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenConstructedWithItem_thenGettersReadable() {
        BootOpsException exception = new BootOpsException("Forced Failure");
        BootOpsExceptionEvent event = new BootOpsExceptionEvent(this, item, exception);

        assertSame(this, event.getSource());
        assertSame(item, event.getItem());
        assertSame(exception, event.getException());
    }

    @Test
    public void testConstructor_whenConstructedWithNullItem_thenGettersReadable() {
        BootOpsException exception = new BootOpsException("Forced Failure");
        BootOpsExceptionEvent event = new BootOpsExceptionEvent(this, null, exception);

        assertSame(this, event.getSource());
        assertNull(event.getItem());
        assertSame(exception, event.getException());
    }

    @Test
    public void testConstructor_whenConstructedWithNoItem_thenGettersReadable() {
        BootOpsException exception = new BootOpsException("Forced Failure");
        BootOpsExceptionEvent event = new BootOpsExceptionEvent(this, exception);

        assertSame(this, event.getSource());
        assertNull(event.getItem());
        assertSame(exception, event.getException());
    }

}