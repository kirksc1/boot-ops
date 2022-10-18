package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

class ItemValidationCompletedEventTest {

    private Item item = mock(Item.class);
    private ItemValidationResult result = mock(ItemValidationResult.class);

    @BeforeEach
    public void beforeEach() {
        reset(item, result);
    }

    @Test
    public void testConstructor_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemValidationCompletedEvent(this, null, result);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenResultNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemValidationCompletedEvent(this, item, null);
        });

        Assertions.assertEquals("The ItemValidationResult provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenConstructed_thenGettersReadable() {
        ItemValidationCompletedEvent event = new ItemValidationCompletedEvent(this, item, result);

        assertSame(this, event.getSource());
        assertSame(item, event.getItem());
        assertSame(result, event.getResult());
    }

}