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
import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.ToString;

/**
 * A Spring application event that indicates that the converge process has finished.  This event
 * is intended to signal that converge activities are complete and any post-converge processing
 * can be executed.
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ItemConvergeCompletedEvent extends ItemEvent {

    /**
     * Construct a new ItemConvergeCompletedEvent instance with the provided details.
     * @param source The source that created the event.
     * @param item The item on which the converge process was executed.
     */
    public ItemConvergeCompletedEvent(Object source, Item item) {
        super(source, item);
    }
}
