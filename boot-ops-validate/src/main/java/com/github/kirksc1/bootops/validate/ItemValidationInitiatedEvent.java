package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

/**
 * A Spring application event that is published when an Item's validation process
 * has been initiated.
 */
@ToString(callSuper=true, includeFieldNames=true)
@Getter
public class ItemValidationInitiatedEvent extends ItemEvent {

    private final ItemValidationResult result;

    /**
     * Construct a new ItemValidationInitiatedEvent with the provided details.
     * @param source The source that created the event.
     * @param item The Item whose execution was completed.
     * @param result The result within which validation results are to be added.
     */
    public ItemValidationInitiatedEvent(Object source, Item item, ItemValidationResult result) {
        super(source, item);

        Assert.notNull(result, "The ItemValidationResult provided was null");

        this.result = result;
    }
}
