package com.github.kirksc1.bootops.core.init;

import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class ItemInitializationExecutorTest {

    private ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

    @BeforeEach
    public void beforeEach() {
        reset(publisher);
    }

    @Test
    public void testConstructor_whenPublisherNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemInitializationExecutor(null);
        });

        Assertions.assertEquals("The ApplicationEventPublisher provided was null", thrown.getMessage());
    }

    @Test
    public void testInitiateInitialization_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemInitializationExecutor(publisher).initiateInitialization(null);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testInitiateInitialization_whenCalled_thenPublishItemInitializationInitiatedEvent() {
        ItemInitializationExecutor executor = new ItemInitializationExecutor(publisher);

        Item item = new Item();
        executor.initiateInitialization(item);

        ArgumentCaptor<ItemInitializationInitiatedEvent> eventCaptor = ArgumentCaptor.forClass(ItemInitializationInitiatedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertSame(item, eventCaptor.getValue().getItem());
    }

    @Test
    public void testCompleteInitialization_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemInitializationExecutor(publisher).completeInitialization(null);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testCompleteInitialization_whenCalled_thenPublishItemInitializationCompletedEvent() {
        ItemInitializationExecutor executor = new ItemInitializationExecutor(publisher);

        Item item = new Item();
        executor.completeInitialization(item);

        ArgumentCaptor<ItemInitializationCompletedEvent> eventCaptor = ArgumentCaptor.forClass(ItemInitializationCompletedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertSame(item, eventCaptor.getValue().getItem());
    }
}