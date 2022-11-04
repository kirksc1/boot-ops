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
package com.github.kirksc1.bootops.sample.storage;

import com.github.kirksc1.bootops.converge.ItemConvergeInitiatedEvent;
import com.github.kirksc1.bootops.core.Item;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

import java.util.Optional;

public class StorageItemConvergeInitiatedEventListener implements ApplicationListener<ItemConvergeInitiatedEvent> {
    private final ItemStorageConvergeManager manager;

    public StorageItemConvergeInitiatedEventListener(ItemStorageConvergeManager manager) {
        Assert.notNull(manager, "The ItemStorageConvergeManager provided was null");

        this.manager = manager;
    }

    //INFO: Consider converging to storage when Converge has been initiated.
    @Override
    public void onApplicationEvent(ItemConvergeInitiatedEvent event) {
        Item item = event.getItem();

        //INFO: Converge item to Storage ONLY if it contains a StorageAttribute (Validation ensures its valid)
        Optional.ofNullable(item)
                .map(Item::getAttributes)
                .map(attributeMap -> attributeMap.get(StorageAttribute.ATTRIBUTE_NAME))
                .filter(StorageAttribute.class::isInstance)
                .map(StorageAttribute.class::cast)
                .ifPresent(attribute -> manager.convergeToStorage(item, attribute));
    }

}
