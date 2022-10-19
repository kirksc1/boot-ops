package com.github.kirksc1.bootops.converge;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.ToString;

/**
 * A Spring application event that indicates that the converge process has been started.  This event
 * is intended to signal that converge activities should be executed.
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ItemConvergeInitiatedEvent extends ItemEvent {

    /**
     * Construct a new ItemConvergeInitiatedEvent instance with the provided details.
     * @param source The source that created the event.
     * @param item The item on which the converge process is executing.
     */
    public ItemConvergeInitiatedEvent(Object source, Item item) {
        super(source, item);
    }
}
