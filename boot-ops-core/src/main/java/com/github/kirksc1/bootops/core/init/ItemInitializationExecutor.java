package com.github.kirksc1.bootops.core.init;

import com.github.kirksc1.bootops.core.Item;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

/**
 * ItemInitializationExecutor manages the process of initializing an Item through publishing
 * the appropriate events.
 */
public class ItemInitializationExecutor {

    private ApplicationEventPublisher eventPublisher;

    /**
     * Construct a new ItemInitializationExecutor with the provided details.
     * @param eventPublisher The Spring ApplicationEventPublisher.
     */
    public ItemInitializationExecutor(ApplicationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "The ApplicationEventPublisher provided was null");

        this.eventPublisher = eventPublisher;
    }

    /**
     * Initiate the Item initialization process for the provided Item.
     * @param item The item to initialize.
     */
    public void initiateInitialization(Item item) {
        eventPublisher.publishEvent(new ItemInitializationInitiatedEvent(this, item));
    }

    /**
     * Complete the Item initialization process for the provided Item.
     * @param item The item that was initialized.
     */
    public void completeInitialization(Item item) {
        eventPublisher.publishEvent(new ItemInitializationCompletedEvent(this, item));
    }
}
