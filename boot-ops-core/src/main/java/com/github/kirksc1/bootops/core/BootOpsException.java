package com.github.kirksc1.bootops.core;

import java.io.Serializable;

/**
 * A wrapper exception for any exception that occurs during the BootOps process.
 */
public class BootOpsException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 95829348759235L;

    /**
     * Construct a new BootOpsException with the provided details.
     * @param message A message describing the reason for failure.
     * @param cause The root cause of the failure.
     */
    public BootOpsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a new BootOpsException with the provided details.
     * @param message A message describing the reason for failure.
     */
    public BootOpsException(String message) {
        super(message);
    }
}
