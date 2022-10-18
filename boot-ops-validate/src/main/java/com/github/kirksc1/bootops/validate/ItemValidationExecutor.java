package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

/**
 * ItemValidationExecutor manages the process of validation an Item through publishing
 * the appropriate events.
 */
public class ItemValidationExecutor {

    private ApplicationEventPublisher eventPublisher;

    /**
     * Construct a new ItemValidationExecutor with the provided details.
     * @param eventPublisher The Spring ApplicationEventPublisher.
     */
    public ItemValidationExecutor(ApplicationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "The ApplicationEventPublisher provided was null");

        this.eventPublisher = eventPublisher;
    }

    /**
     * Initiate the Item validation process for the provided Item.
     * @param item The item to validate.
     */
    public ItemValidationResult initiateValidation(Item item) {
        Assert.notNull(item, "The Item provided was null");

        ItemValidationResult validationResult = new DefaultItemValidationResult();

        eventPublisher.publishEvent(new ItemValidationInitiatedEvent(this, item, validationResult));

        return  validationResult;
    }

    /**
     * Complete the Item validation process for the provided Item.
     * @param item The item that was validated.
     * @param result The result of the validation process.
     */
    public void completeValidation(Item item, ItemValidationResult result) {
        Assert.notNull(item, "The Item provided was null");
        Assert.notNull(result, "The ItemValidationResult provided was null");

        eventPublisher.publishEvent(new ItemValidationCompletedEvent(this, item, result));
    }
}
