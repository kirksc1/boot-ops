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

import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

/**
 * ItemBeanValidationListener is a ItemValidationInitiatedEvent implementation that executes JSR-380 Bean
 * Validation.
 */
public class ItemBeanValidationListener implements ApplicationListener<ItemValidationInitiatedEvent> {

    private ItemBeanValidator validator;

    /**
     * Construct a new instance with the provided Item validator.
     * @param validator An Item bean validator.
     */
    public ItemBeanValidationListener(ItemBeanValidator validator) {
        Assert.notNull(validator, "The ItemBeanValidator provided was null");

        this.validator = validator;
    }

    /**
     * Validate the item and add the results to the ItemValidationResult provided within the ItemValidationInitiatedEvent.
     * @param event The ItemValidationInitiatedEvent.
     */
    @Override
    public void onApplicationEvent(ItemValidationInitiatedEvent event) {
        validator.validate(event.getItem(), event.getResult());
    }
}
