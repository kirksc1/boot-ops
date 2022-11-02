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
package com.github.kirksc1.bootops.core.system;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

/**
 * SystemExecutor manages the process of the system through publishing
 * the appropriate events.
 */
public class SystemExecutor {

    private ApplicationEventPublisher eventPublisher;

    /**
     * Construct a new SystemExecutor with the provided details.
     * @param eventPublisher The Spring ApplicationEventPublisher.
     */
    public SystemExecutor(ApplicationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "The ApplicationEventPublisher provided was null");

        this.eventPublisher = eventPublisher;
    }

    /**
     * Initiate the system process.
     */
    public void initiateSystem() {
        eventPublisher.publishEvent(new SystemInitiatedEvent(this));
    }

    /**
     * Complete the system process.
     */
    public void completeSystem() {
        eventPublisher.publishEvent(new SystemCompletedEvent(this));
    }
}
