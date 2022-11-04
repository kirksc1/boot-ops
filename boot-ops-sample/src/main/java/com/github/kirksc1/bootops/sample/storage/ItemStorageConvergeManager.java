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

import com.github.kirksc1.bootops.core.BootOpsException;
import com.github.kirksc1.bootops.core.Item;
import org.springframework.context.ApplicationEventPublisher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class ItemStorageConvergeManager {

    private final ApplicationEventPublisher publisher;
    private final StorageServiceClient serviceClient;

    public ItemStorageConvergeManager(ApplicationEventPublisher publisher, StorageServiceClient serviceClient) {
        this.publisher = publisher;
        this.serviceClient = serviceClient;
    }

    public void convergeToStorage(Item item, StorageAttribute attribute) {
        //NOTE: This is just a sample method of converging an Item that checks the current state and create/updates it as necessary.
        //      You should determine the best converge strategy for your use case.

        try {
            Optional<StoredItem> storedItemOptional = serviceClient.readStoredItem(new URL(attribute.getLocation()));
            StoredItem itemStoredItem = buildStoredItem(item, attribute);

            if (storedItemOptional.isPresent()) {
                serviceClient.updateStoredItem(overlay(itemStoredItem, storedItemOptional.get()));

                //INFO: publish updated event if other processes need to trigger off of this state change
                publisher.publishEvent(new StoredItemUpdatedEvent(this, item));
            } else {
                //NOTE: Sample un-implemented StorageServiceClient code returns Optional.emtpy() so this is always be the result
                serviceClient.createStoredItem(itemStoredItem);

                //INFO: publish created event if other processes need to trigger off of this state change
                publisher.publishEvent(new StoredItemCreatedEvent(this, item));
            }
        } catch (MalformedURLException e) {
            //INFO: Wrap exceptions in BootOpsExceptions to capture metrics and a summary of all issues
            throw new BootOpsException("Unable to store item to " + attribute.getLocation(), e);
        }

    }

    private StoredItem overlay(StoredItem source, StoredItem target) {
        //NOTE: insert logic to overlay the source data onto the target
        return null;
    }

    private StoredItem buildStoredItem(Item item, StorageAttribute attribute) {
        //NOTE: insert logic to convert Item to StoredItem
        return null;
    }
}
