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
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

/**
 * DefaultItemValidationResult is the default implementation of the ItemValidationResult
 * interface.
 */
public class DefaultItemValidationResult implements ItemValidationResult {

    private final Set<ConstraintViolation<Item>> violations = new HashSet<>();

    /**
     * @inheritDoc
     */
    @Override
    public boolean isValid() {
        return violations.isEmpty();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addViolations(Set<ConstraintViolation<Item>> violations) {
        Assert.notNull(violations, "The Set of ConstraintViolations provided was null");

        this.violations.addAll(violations);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addViolation(ConstraintViolation<Item> violation) {
        Assert.notNull(violation, "The ConstraintViolation provided was null");

        this.violations.add(violation);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<ConstraintViolation<Item>> getViolations() {
        return violations;
    }
}
