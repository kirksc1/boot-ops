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
package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

/**
 * A Spring application event that is published when an Item's validation process
 * has completed.
 */
@ToString(callSuper=true, includeFieldNames=true)
@Getter
public class ItemValidationCompletedEvent extends ItemEvent {

    private final ItemValidationResult result;

    /**
     * Construct a new ItemValidationCompletedEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item whose execution was completed.
     * @param result The result of the validation process.
     */
    public ItemValidationCompletedEvent(Object source, Item item, ItemValidationResult result) {
        super(source, item);

        Assert.notNull(result, "The ItemValidationResult provided was null");

        this.result = result;
    }
}
