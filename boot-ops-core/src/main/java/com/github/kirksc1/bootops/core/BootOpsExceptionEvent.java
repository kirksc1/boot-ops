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

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

/**
 * A Spring application event that will be published when a BootOpsException occurs.
 */
@Getter
@ToString(callSuper=true, includeFieldNames=true)
public class BootOpsExceptionEvent extends ApplicationEvent {

    private static final long serialVersionUID = 29587983742594526L;

    private final Item item;
    private final BootOpsException exception;

    /**
     * Construct a BootOpsExceptionEvent from the provided details.
     * @param source The source that created the event.
     * @param item The item associated with the event.
     * @param exception The BootOpsException that occurred.
     */
    public BootOpsExceptionEvent(Object source, Item item, BootOpsException exception) {
        super(source);

        Assert.notNull(exception, "The BootOpsException provided was null");

        this.item = item;
        this.exception = exception;
    }

    /**
     * Construct a BootOpsExceptionEvent from the provided details.
     * @param source The source that created the event.
     * @param exception The BootOpsException that occurred.
     */
    public BootOpsExceptionEvent(Object source, BootOpsException exception) {
        this(source, null, exception);
    }

}
