package com.github.kirksc1.bootops.core;

import lombok.ToString;

/**
 * A Spring application event that is published when a command's processing has been
 * completed on an Item.
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ItemCompletedEvent extends ItemEvent {

    private static final long serialVersionUID = 9257849250234L;

    /**
     * Construct a new ItemCompletedEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item whose execution was completed.
     */
    public ItemCompletedEvent(Object source, Item item) {
        super(source, item);
    }
}
