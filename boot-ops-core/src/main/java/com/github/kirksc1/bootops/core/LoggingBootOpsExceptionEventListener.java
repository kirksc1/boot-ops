package com.github.kirksc1.bootops.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * LoggingBootOpsExceptionEventListener is an event listener that logs all BootOpsExceptionEvents at the error level.
 */
@Slf4j
public class LoggingBootOpsExceptionEventListener implements ApplicationListener<BootOpsExceptionEvent> {

    /**
     * Log the provided event at the error level as a failure.  Include item information if present.
     * @param event The event to be logged.
     */
    @Override
    public void onApplicationEvent(BootOpsExceptionEvent event) {
        if (event.getItem() == null) {
            log.error("Unable to complete processing", event.getException());
        } else {
            log.error("Unable to complete processing of item '{}'", event.getItem().getName(), event.getException());
        }
    }
}
