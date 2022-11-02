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
package com.github.kirksc1.bootops.core;

import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * A Spring application event that represents a system event.
 */
@ToString(callSuper = true, includeFieldNames = true)
public abstract class SystemEvent extends ApplicationEvent {

    private static final long serialVersionUID = 697262045245L;

    /**
     * Construct a new instance with the provided details.
     * @param source The source that created the event.
     */
    protected SystemEvent(Object source) {
        super(source);
    }
}
