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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

/**
 * ItemInitializationExecutor manages the process of initializing an Item through publishing
 * the appropriate events.
 */
public class ItemInitializationExecutor {

    private ApplicationEventPublisher eventPublisher;

    /**
     * Construct a new ItemInitializationExecutor with the provided details.
     * @param eventPublisher The Spring ApplicationEventPublisher.
     */
    public ItemInitializationExecutor(ApplicationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "The ApplicationEventPublisher provided was null");

        this.eventPublisher = eventPublisher;
    }

    /**
     * Initiate the Item initialization process for the provided Item.
     * @param item The item to initialize.
     */
    public void initiateInitialization(Item item) {
        Assert.notNull(item, "The Item provided was null");

        eventPublisher.publishEvent(new ItemInitializationInitiatedEvent(this, item));
    }

    /**
     * Complete the Item initialization process for the provided Item.
     * @param item The item that was initialized.
     */
    public void completeInitialization(Item item) {
        Assert.notNull(item, "The Item provided was null");
        
        eventPublisher.publishEvent(new ItemInitializationCompletedEvent(this, item));
    }
}
