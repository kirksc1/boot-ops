package com.github.kirksc1.bootops.core.init;

import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

class ItemInitializationCompletedEventTest {

    private Item item = mock(Item.class);

    @BeforeEach
    public void beforeEach() {
        reset(item);
    }

    @Test
    public void testConstructor_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemInitializationCompletedEvent(this, null);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenConstructed_thenGettersReadable() {
        ItemInitializationCompletedEvent event = new ItemInitializationCompletedEvent(this, item);

        assertSame(this, event.getSource());
        assertSame(item, event.getItem());
    }

}