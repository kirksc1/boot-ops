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

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * The ItemValidationResult encapsulates the information pertaining to the result of
 * a validation process executed on the item.  Failed validations take the form of a
 * ConstraintViolation<Item>.
 */
public interface ItemValidationResult {

    /**
     * Check to see if the Item was valid.
     * @return True if no ConstraintViolations occurred, otherwise false.
     */
    boolean isValid();

    /**
     * Add the provided set of ConstraintViolations to the result.
     * @param violations A set of ConstraintViolations that have occurred on the Item.
     */
    void addViolations(Set<ConstraintViolation<Item>> violations);

    /**
     * Add the provided set of ConstraintViolations to the result.
     * @param violation A ConstraintViolation that has occurred on the Item.
     */
    void addViolation(ConstraintViolation<Item> violation);

    /**
     * Retrieve the set of ConstraintViolations that occurred on the Item.
     * @return The set of ConstraintViolations.
     */
    Set<ConstraintViolation<Item>> getViolations();
}
