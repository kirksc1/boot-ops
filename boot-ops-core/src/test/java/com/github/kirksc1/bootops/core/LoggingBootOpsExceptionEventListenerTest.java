package com.github.kirksc1.bootops.core;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingBootOpsExceptionEventListenerTest {

    private ListAppender<ILoggingEvent> logWatcher;
    private Logger logger;

    @BeforeEach
    public void beforeEach() {
        logWatcher = new ListAppender<>();
        logWatcher.start();

        logger = (Logger) LoggerFactory.getLogger(LoggingBootOpsExceptionEventListener.class);
        logger.addAppender(logWatcher);
    }

    @AfterEach
    public void afterEach() {
        logger.detachAndStopAllAppenders();
    }

    @Test
    public void testOnApplicationEvent_whenItemPresent_thenLogException() {
        LoggingBootOpsExceptionEventListener eventListener = new LoggingBootOpsExceptionEventListener();
        eventListener.onApplicationEvent(new BootOpsExceptionEvent(this, new Item(), new BootOpsException("Forced Failure")));

        int logCount = logWatcher.list.size();
        assertEquals(1, logCount);

        assertTrue(logWatcher.list.get(0).getFormattedMessage().contains("Unable to complete processing of item"), "Item message incorrect");
    }

    @Test
    public void testOnApplicationEvent_whenItemNull_thenLogException() {
        LoggingBootOpsExceptionEventListener eventListener = new LoggingBootOpsExceptionEventListener();
        eventListener.onApplicationEvent(new BootOpsExceptionEvent(this, null, new BootOpsException("Forced Failure")));

        int logCount = logWatcher.list.size();
        assertEquals(1, logCount);

        assertTrue(logWatcher.list.get(0).getFormattedMessage().contains("Unable to complete processing"), "Item message incorrect");
    }

}