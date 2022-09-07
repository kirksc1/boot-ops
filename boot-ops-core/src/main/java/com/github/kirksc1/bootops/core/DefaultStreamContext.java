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
}
