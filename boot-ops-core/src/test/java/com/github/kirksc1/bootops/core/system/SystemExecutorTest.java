package com.github.kirksc1.bootops.core.system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SystemExecutorTest {

    private ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

    @BeforeEach
    public void beforeEach() {
        reset(publisher);
    }

    @Test
    public void testConstructor_whenPublisherNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SystemExecutor(null);
        });

        Assertions.assertEquals("The ApplicationEventPublisher provided was null", thrown.getMessage());
    }

    @Test
    public void testInitiateSystem_whenCalled_thenPublishSystemInitiatedEvent() {
        SystemExecutor executor = new SystemExecutor(publisher);

        executor.initiateSystem();

        ArgumentCaptor<SystemInitiatedEvent> eventCaptor = ArgumentCaptor.forClass(SystemInitiatedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertNotNull(eventCaptor.getValue());
    }

    @Test
    public void testCompleteSystem_whenCalled_thenPublishSystemCompletedEvent() {
        SystemExecutor executor = new SystemExecutor(publisher);

        executor.completeSystem();

        ArgumentCaptor<SystemCompletedEvent> eventCaptor = ArgumentCaptor.forClass(SystemCompletedEvent.class);
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        assertNotNull(eventCaptor.getValue());
    }

}