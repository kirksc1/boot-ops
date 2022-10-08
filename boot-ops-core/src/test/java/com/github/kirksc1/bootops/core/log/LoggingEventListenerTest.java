package com.github.kirksc1.bootops.core.log;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.github.kirksc1.bootops.core.Item;
import com.github.kirksc1.bootops.core.ItemCompletedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingEventListenerTest {

    private ListAppender<ILoggingEvent> logWatcher;
    private Logger logger;

    @BeforeEach
    public void beforeEach() {
        logWatcher = new ListAppender<>();
        logWatcher.start();

        logger = (Logger) LoggerFactory.getLogger(LoggingEventListener.class);
        logger.addAppender(logWatcher);
    }

    @AfterEach
    public void afterEach() {
        logger.detachAndStopAllAppenders();
    }

    @Test
    public void testOnItemEvent_whenEventReceived_thenLogEvent() {
        LoggingEventListener eventListener = new LoggingEventListener();
        eventListener.onItemEvent(new ItemCompletedEvent(this, new Item()));

        int logCount = logWatcher.list.size();
        assertEquals(1, logCount);

        assertTrue(logWatcher.list.get(0).getFormattedMessage().contains("ItemCompletedEvent"), "Event message missing");
    }
}