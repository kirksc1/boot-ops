package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ItemValidationExecutorTest {

    private ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

    @BeforeEach
    public void beforeEach() {
        reset(publisher);
    }

    @Test
    public void testConstructor_whenPublisherNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemValidationExecutor(null);
        });

        Assertions.assertEquals("The ApplicationEventPublisher provided was null", thrown.getMessage());
    }

    @Test
    public void testInitiateValidation_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemValidationExecutor(publisher).initiateValidation(null);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testInitiateValidation_whenCalled_thenPublishItemValidationInitiatedEvent() {
        ItemValidationExecutor executor = new ItemValidationExecutor(publisher);

        Item item = new Item();
        executor.initiateValidation(item);

        ArgumentCaptor<ItemValidationInitiatedEvent> eventCaptor = ArgumentCaptor.forClass(ItemValidationInitiatedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertSame(item, eventCaptor.getValue().getItem());
        assertNotNull(eventCaptor.getValue().getResult());
    }

    @Test
    public void testCompleteValidation_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemValidationExecutor(publisher).completeValidation(null, new DefaultItemValidationResult());
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testCompleteValidation_whenResultNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemValidationExecutor(publisher).completeValidation(new Item(), null);
        });

        Assertions.assertEquals("The ItemValidationResult provided was null", thrown.getMessage());
    }

    @Test
    public void testCompleteValidation_whenCalled_thenPublishItemValidationCompletedEvent() {
        ItemValidationExecutor executor = new ItemValidationExecutor(publisher);

        Item item = new Item();
        DefaultItemValidationResult result = new DefaultItemValidationResult();
        executor.completeValidation(item, result);

        ArgumentCaptor<ItemValidationCompletedEvent> eventCaptor = ArgumentCaptor.forClass(ItemValidationCompletedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertSame(item, eventCaptor.getValue().getItem());
        assertSame(result, eventCaptor.getValue().getResult());
    }

}