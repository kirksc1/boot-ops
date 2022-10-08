package com.github.kirksc1.bootops.core.log;

import com.github.kirksc1.bootops.core.ItemEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

/**
 * LoggingEventListener is an event listener that logs all Boot Ops events that occur.
 */
@Slf4j
public class LoggingEventListener {

    /**
     * Log the details of the ItemEvent provided.
     * @param event The ItemEvent that was published.
     */
    @EventListener
    public void onItemEvent(ItemEvent event) {
        log.debug("{}", event);
    }
}
