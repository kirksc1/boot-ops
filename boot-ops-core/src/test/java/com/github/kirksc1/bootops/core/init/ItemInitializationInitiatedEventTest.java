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