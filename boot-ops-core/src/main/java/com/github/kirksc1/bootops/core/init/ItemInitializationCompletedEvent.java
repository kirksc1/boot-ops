package com.github.kirksc1.bootops.core.init;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.ToString;

/**
 * A Spring application event that is published when an Item's initialization process
 * has completed.
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ItemInitializationCompletedEvent extends ItemEvent {

    /**
     * Construct a new ItemInitializationCompletedEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item whose execution was completed.
     */
    public ItemInitializationCompletedEvent(Object source, Item item) {
        super(source, item);
    }
}
