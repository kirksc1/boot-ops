package com.github.kirksc1.bootops.core;

/**
 * A ExecutionResult defines the required information that must be communicated back from processing.
 */
public interface ExecutionResult {

    /**
     * Check to see if the processing was completed successfully.
     * @return True if it was successful, otherwise false.
     */
    boolean isSuccessful();

    /**
     * Retrieve the cause of the failure if one occurred.
     * @return The cause of the failure if one occurred, otherwise null.
     */
    Throwable getFailureCause();
}
