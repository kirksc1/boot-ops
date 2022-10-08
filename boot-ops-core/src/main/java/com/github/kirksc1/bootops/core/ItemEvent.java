package com.github.kirksc1.bootops.core;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

/**
 * A Spring application event that references an Item or is associated with an Item.
 */
@Getter
@ToString(callSuper=true, includeFieldNames=true)
public abstract class ItemEvent extends ApplicationEvent {

    private static final long serialVersionUID = 234957983476L;

    private final Item item;

    /**
     * Construct a new ItemEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item to which the event relates.
     */
    protected ItemEvent(Object source, Item item) {
        super(source);

        Assert.notNull(item, "The Item provided was null");

        this.item = item;
    }
}
