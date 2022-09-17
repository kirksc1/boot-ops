package com.github.kirksc1.bootops.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.net.URI;
import java.util.LinkedHashMap;

/**
 * An Item represents any configurable thing within the system.
 */
@Getter
@Setter
public class Item implements Serializable {

    private static final long serialVersionUID = 1295728345628572L;

    /**
     * A URI representing the location of the configuration.
     */
    private URI uri;

    /**
     * A name identifying the configurable item.
     */
    private String name;

    /**
     * A Map of attribute configuration for the item.
     */
    private final LinkedHashMap<String,Object> attributes = new LinkedHashMap<>();
}
