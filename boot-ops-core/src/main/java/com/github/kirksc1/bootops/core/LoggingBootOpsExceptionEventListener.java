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
