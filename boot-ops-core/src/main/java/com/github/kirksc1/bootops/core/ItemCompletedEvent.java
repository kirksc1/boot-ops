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

/**
 * A Spring application event that is published when a command's processing has been
 * completed on an Item.
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ItemCompletedEvent extends ItemEvent {

    private static final long serialVersionUID = 9257849250234L;

    /**
     * Construct a new ItemCompletedEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item whose execution was completed.
     */
    public ItemCompletedEvent(Object source, Item item) {
        super(source, item);
    }
}
