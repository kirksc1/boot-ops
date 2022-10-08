package com.github.kirksc1.bootops.core.init;

import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

class ItemInitializationInitiatedEventTest {

    private Item item = mock(Item.class);

    @BeforeEach
    public void beforeEach() {
        reset(item);
    }

    @Test
    public void testConstructor_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemInitializationInitiatedEvent(this, null);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenConstructed_thenGettersReadable() {
        ItemInitializationInitiatedEvent event = new ItemInitializationInitiatedEvent(this, item);

        assertSame(this, event.getSource());
        assertSame(item, event.getItem());
    }

}