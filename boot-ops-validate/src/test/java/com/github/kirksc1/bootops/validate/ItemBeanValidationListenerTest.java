package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ItemBeanValidationListenerTest {

    @Test
    public void testConstructor_whenValdiatorNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemBeanValidationListener( null);
        });

        Assertions.assertEquals("The ItemBeanValidator provided was null", thrown.getMessage());
    }

    @Test
    public void testOnApplicationEvent_whenEvent_thenCallValidator() {
        ItemBeanValidator validator = mock(ItemBeanValidator.class);
        ItemBeanValidationListener listener = new ItemBeanValidationListener(validator);

        ItemValidationInitiatedEvent event = mock(ItemValidationInitiatedEvent.class);
        Item item = mock(Item.class);
        when(event.getItem()).thenReturn(item);

        ItemValidationResult result = mock(ItemValidationResult.class);
        when(event.getResult()).thenReturn(result);

        listener.onApplicationEvent(event);

        verify(validator, times(1)).validate(same(item), same(result));
    }

}