package com.github.kirksc1.bootops.core;

public class ParseException extends BootOpsException {
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(String message) {
        super(message);
    }
}
