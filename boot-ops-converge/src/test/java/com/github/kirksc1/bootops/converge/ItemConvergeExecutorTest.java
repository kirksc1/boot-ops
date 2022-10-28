/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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