package com.github.kirksc1.bootops.converge;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.ToString;

/**
 * A Spring application event that indicates that the converge process has finished.  This event
 * is intended to signal that converge activities are complete and any post-converge processing
 * can be executed.
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ItemConvergeCompletedEvent extends ItemEvent {

    /**
     * Construct a new ItemConvergeCompletedEvent instance with the provided details.
     * @param source The source that created the event.
     * @param item The item on which the converge process was executed.
     */
    public ItemConvergeCompletedEvent(Object source, Item item) {
        super(source, item);
    }
}
