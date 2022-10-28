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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The DefaultStreamContext is the default implementation of a StreamContext.  It stores its contextual
 * data within an in-memory Map.
 */
public class DefaultStreamContext implements StreamContext {
    private final Map<String,Object> contextMap = new HashMap<>();

    /**
     * @inheritDoc
     */
    public void put(String key, Object value) {
        Assert.notNull(key, "The key provided was null");

        this.contextMap.put(key, value);
    }

    /**
     * @inheritDoc
     */
    public Object remove(String key) {
        Assert.notNull(key, "The key provided was null");

        return contextMap.remove(key);
    }

    /**
     * @inheritDoc
     */
    public Object get(String key) {
        Assert.notNull(key, "The key provided was null");

        return contextMap.get(key);
    }

    /**
     * @inheritDoc
     */
    public Set<String> keySet() {
        return contextMap.keySet();
    }

    /**
     * @inheritDoc
     */
    public boolean containsKey(String key) {
        return contextMap.containsKey(key);
    }
}
