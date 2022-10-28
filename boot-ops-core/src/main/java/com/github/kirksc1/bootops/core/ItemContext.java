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

import java.util.Set;

/**
 * The StreamContext is a named Object Map that can be used to store information across item processing.
 */
public interface ItemContext {

    /**
     * Put the Object into the context under the provided key.
     * @param key The identifying key.
     * @param value The Object value.
     */
    void put(String key, Object value);

    /**
     * Remove the Object identified by the provided key.
     * @param key The key of the Object to be removed.
     * @return The removed Object if one was present, otherwise null.
     */
    Object remove(String key);

    /**
     * Retrieve the Object identified by the provided key.
     * @param key The key of the Object to be retrieved.
     * @return The Object.
     */
    Object get(String key);

    /**
     * Retrieve the Set of keys for all Objects within the context.
     * @return The Set of keys.
     */
    Set<String> keySet();

    /**
     * Check to see if an entry for the key exists in the context.
     * @param key The key to check.
     * @return True if the key exists, otherwise false.
     */
    boolean containsKey(String key);
}
