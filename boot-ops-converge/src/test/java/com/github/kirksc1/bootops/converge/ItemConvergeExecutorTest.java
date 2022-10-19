package com.github.kirksc1.bootops.converge;

import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class ItemConvergeExecutorTest {

    private ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

    @BeforeEach
    public void beforeEach() {
        reset(publisher);
    }

    @Test
    public void testConstructor_whenPublisherNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemConvergeExecutor(null);
        });

        Assertions.assertEquals("The ApplicationEventPublisher provided was null", thrown.getMessage());
    }

    @Test
    public void testInitiateConverge_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemConvergeExecutor(publisher).initiateConverge(null);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testInitiateConverge_whenCalled_thenPublishItemConvergeInitiatedEvent() {
        ItemConvergeExecutor executor = new ItemConvergeExecutor(publisher);

        Item item = new Item();
        executor.initiateConverge(item);

        ArgumentCaptor<ItemConvergeInitiatedEvent> eventCaptor = ArgumentCaptor.forClass(ItemConvergeInitiatedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertSame(item, eventCaptor.getValue().getItem());
    }

    @Test
    public void testCompleteInitialization_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemConvergeExecutor(publisher).completeConverge(null);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testCompleteConverge_whenCalled_thenPublishItemConvergeCompletedEvent() {
        ItemConvergeExecutor executor = new ItemConvergeExecutor(publisher);

        Item item = new Item();
        executor.completeConverge(item);

        ArgumentCaptor<ItemConvergeCompletedEvent> eventCaptor = ArgumentCaptor.forClass(ItemConvergeCompletedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertSame(item, eventCaptor.getValue().getItem());
    }

}