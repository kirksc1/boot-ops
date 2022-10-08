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
