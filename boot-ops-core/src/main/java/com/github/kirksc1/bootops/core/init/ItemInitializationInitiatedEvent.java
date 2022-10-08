package com.github.kirksc1.bootops.core.init;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.ToString;

/**
 * A Spring application event that is published when an Item's initialization process
 * is starting.
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ItemInitializationInitiatedEvent extends ItemEvent {

    /**
     * Construct a new ItemInitializationInitiatedEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item whose execution was completed.
     */
    public ItemInitializationInitiatedEvent(Object source, Item item) {
        super(source, item);
    }
}
