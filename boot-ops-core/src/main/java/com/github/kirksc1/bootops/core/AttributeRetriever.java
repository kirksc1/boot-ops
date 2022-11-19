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

import org.springframework.util.Assert;

import java.util.Optional;

/**
 * AttributeRetriever is a component designed to retrieve a specific named attribute as a specific class type.
 * It only retrieves a value if there is a value as the named attribute and if it can be cast into the specific
 * class.
 *
 * AttributeRetriever is intended to be constructed as a bean and injectable into logic to reduce the complexity
 * of dealing with attributes.
 * @param <T> The type of class to retrieve.
 */
public class AttributeRetriever<T> {

    private final String attributeName;
    private final Class<T> attributeClass;

    /**
     * Construct a new instance to retrieve the attribute defined by the provided details.
     * @param attributeName The name of the attribute.
     * @param attributeClass The attribute class.
     */
    public AttributeRetriever(String attributeName, Class<T> attributeClass) {
        Assert.notNull(attributeName, "The attribute name provided was null");
        Assert.notNull(attributeClass, "The attribute class provided was null");

        this.attributeName = attributeName;
        this.attributeClass = attributeClass;
    }

    /**
     * Retrieve the specified attribute if available.
     * @param item The item from which to retrieve the attribute.
     * @return Optionally, the attribute instance if found and castable into the attribute class, otherwise empty.
     */
    public Optional<T> retrieve(Item item) {
        return Optional.ofNullable(item)
                .map(Item::getAttributes)
                .map(stringObjectLinkedHashMap -> stringObjectLinkedHashMap.get(attributeName))
                .filter(attributeClass::isInstance)
                .map(attributeClass::cast);
    }
}
