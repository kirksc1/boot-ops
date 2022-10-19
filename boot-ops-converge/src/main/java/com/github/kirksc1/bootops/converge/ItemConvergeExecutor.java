package com.github.kirksc1.bootops.converge;

import com.github.kirksc1.bootops.core.Item;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

/**
 * ItemConvergeExecutor manages the process of converging an Item through publishing
 * the appropriate events.
 */
public class ItemConvergeExecutor {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Construct a new ItemConvergeExecutor with the provided details.
     * @param eventPublisher The Spring ApplicationEventPublisher.
     */
    public ItemConvergeExecutor(ApplicationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "The ApplicationEventPublisher provided was null");

        this.eventPublisher = eventPublisher;
    }

    /**
     * Initiate the Item converge process for the provided Item.
     * @param item The item to converge.
     */
    public void initiateConverge(Item item) {
        Assert.notNull(item, "The Item provided was null");

        eventPublisher.publishEvent(new ItemConvergeInitiatedEvent(this, item));
    }

    /**
     * Complete the Item converge process for the provided Item.
     * @param item The item that was validated.
     */
    public void completeConverge(Item item) {
        Assert.notNull(item, "The Item provided was null");

        eventPublisher.publishEvent(new ItemConvergeCompletedEvent(this, item));
    }
}
