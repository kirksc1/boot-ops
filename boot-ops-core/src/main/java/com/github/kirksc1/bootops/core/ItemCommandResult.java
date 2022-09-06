package com.github.kirksc1.bootops.core;

/**
 * A ItemCommandResult defines the required information that must be communicated back from processing of an Item
 * by a command.
 */
public interface ItemCommandResult extends ExecutionResult {
    /**
     * Retrieve the Item to which the result applies.
     * @return The Item to which the result applies.
     */
    Item getItem();
}
