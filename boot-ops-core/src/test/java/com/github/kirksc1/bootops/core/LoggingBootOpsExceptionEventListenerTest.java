/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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