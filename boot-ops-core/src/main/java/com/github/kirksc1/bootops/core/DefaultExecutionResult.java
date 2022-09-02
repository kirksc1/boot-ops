package com.github.kirksc1.bootops.core;

/**
 * A default implementation for the ExecutionResult interface.
 */
public class DefaultExecutionResult implements ExecutionResult {

    private boolean succeessful = false;
    private Throwable throwable = null;

    /**
     * @inheritDoc
     */
    @Override
    public boolean isSuccessful() {
        return succeessful;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Throwable getFailureCause() {
        return throwable;
    }

    /**
     * Mark the result as successful.
     */
    void succeeded() {
        succeessful = true;
        throwable = null;
    }

    /**
     * Mark the result as failed with the provided Throwable as the cause.
     * @param t The Throwable that caused the failure.
     */
    void failed(Throwable t) {
        succeessful = false;
        throwable = t;
    }
}
