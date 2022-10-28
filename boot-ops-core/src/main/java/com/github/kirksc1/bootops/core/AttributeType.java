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

import lombok.Getter;
import org.springframework.util.Assert;

/**
 * An AttributeType describes the mapping between the name of attribute and the class into which its data should be
 * deserialized.
 */
@Getter
public class AttributeType {
    private final String name;
    private final Class attributeClass;

    /**
     * Construct a new instance with the provided details.
     * @param name The name of the attribute.
     * @param attributeClass The class into which its data should be deserialized.
     */
    public AttributeType(String name, Class attributeClass) {
        Assert.notNull(name, "The name provided was null");
        Assert.notNull(attributeClass, "The class provided was null");

        this.name = name;
        this.attributeClass = attributeClass;
    }
}
