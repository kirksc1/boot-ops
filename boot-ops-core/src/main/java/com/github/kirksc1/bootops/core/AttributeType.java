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
