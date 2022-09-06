package com.github.kirksc1.bootops.core;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A ItemStreamCommandResult defines the required information that must be communicated back from processing of an Item
 * Stream by a command.
 */
@Getter
@Setter
public class ItemStreamCommandResult {
    private final List<ItemCommandResult> results = new ArrayList<>();

    /**
     * Add the result from processing of an Item by a command.
     * @param result The result from processing of an Item by a command.
     */
    public void addResult(ItemCommandResult result) {
        this.results.add(result);
    }

    /**
     * Check to see if the result was sucessful.
     * @return True if all of the processing was successful, otherwise false.
     */
    public boolean isSuccessful() {
        return results.isEmpty() || results.stream().allMatch(ItemCommandResult::isSuccessful);
    }

}
