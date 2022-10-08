package com.github.kirksc1.bootops.core;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

/**
 * A Spring application event that will be published when a BootOpsException occurs.
 */
@Getter
@ToString(callSuper=true, includeFieldNames=true)
public class BootOpsExceptionEvent extends ApplicationEvent {

    private static final long serialVersionUID = 29587983742594526L;

    private final Item item;
    private final BootOpsException exception;

    /**
     * Construct a BootOpsExceptionEvent from the provided details.
     * @param source The source that created the event.
     * @param item The item associated with the event.
     * @param exception The BootOpsException that occurred.
     */
    public BootOpsExceptionEvent(Object source, Item item, BootOpsException exception) {
        super(source);

        Assert.notNull(exception, "The BootOpsException provided was null");

        this.item = item;
        this.exception = exception;
    }

    /**
     * Construct a BootOpsExceptionEvent from the provided details.
     * @param source The source that created the event.
     * @param exception The BootOpsException that occurred.
     */
    public BootOpsExceptionEvent(Object source, BootOpsException exception) {
        this(source, null, exception);
    }

}
