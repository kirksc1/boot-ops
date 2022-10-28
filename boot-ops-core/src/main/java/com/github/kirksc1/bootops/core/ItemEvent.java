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
 * A Spring application event that references an Item or is associated with an Item.
 */
@Getter
@ToString(callSuper=true, includeFieldNames=true)
public abstract class ItemEvent extends ApplicationEvent {

    private static final long serialVersionUID = 234957983476L;

    private final Item item;

    /**
     * Construct a new ItemEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item to which the event relates.
     */
    protected ItemEvent(Object source, Item item) {
        super(source);

        Assert.notNull(item, "The Item provided was null");

        this.item = item;
    }
}
