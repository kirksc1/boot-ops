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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * ItemBeanValidator applies Jakarta Bean Validation to the provided item and adds any violations to the provided
 * result.
 */
public class ItemBeanValidator {

    private final Validator validator;

    /**
     * Construct a new istance with the provided Validator.
     * @param validator The Validator to use for validation.
     */
    public ItemBeanValidator(Validator validator) {
        Assert.notNull(validator, "The Validator provided was null");

        this.validator = validator;
    }

    /**
     * Validate the provided Item and add any violations to the provided ItemValidationResult.
     * @param item The Item to validate.
     * @param result The result into which violations will be populated.
     */
    public void validate(Item item, ItemValidationResult result) {
        Assert.notNull(item, "The Item provided was null");
        Assert.notNull(result, "The ItemValidationResult provided was null");

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        if (!violations.isEmpty()) {
            result.addViolations(violations);
        }
    }
}
