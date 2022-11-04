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

import com.github.kirksc1.bootops.core.AttributeType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

    //INFO: register the client bean for communicating to the service
    @Bean
    public StorageServiceClient storageServiceClient() {
        return new StorageServiceClient();
    }

    //INFO: register the Manager bean to adapt the BootOps Item to the domain of the Storage Client
    @Bean
    public ItemStorageConvergeManager itemStorageConvergeManager(ApplicationEventPublisher publisher, StorageServiceClient serviceClient) {
        return new ItemStorageConvergeManager(publisher, serviceClient);
    }

    //INFO: register the event listener bean to filter out items on basic criteria (presence/absence of data) and delegate to the manager for execution
    @Bean
    public StorageItemConvergeInitiatedEventListener storageItemConvergeInitiatedEventListener(ItemStorageConvergeManager manager) {
        return new StorageItemConvergeInitiatedEventListener(manager);
    }

    //INFO: register the bean that maps the attribute name to the attribute implementation class so that Jackson can deserialize it appropriately.
    @Bean
    public AttributeType storageAttributeType() {
        return new AttributeType(StorageAttribute.ATTRIBUTE_NAME, StorageAttribute.class);
    }

}
