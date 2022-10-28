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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BootOpsExceptionTest {

    @Test
    public void testConstructor_whenOnlyMessage_thenMessageGettable() {
        BootOpsException exception = new BootOpsException("test");

        assertEquals("test", exception.getMessage());
    }

    @Test
    public void testConstructor_whenMessageAndThrowable_thenInfoGettable() {
        Throwable t = new RuntimeException("Forced Failure");
        BootOpsException exception = new BootOpsException("test", t);

        assertEquals("test", exception.getMessage());
        assertSame(t, exception.getCause());
    }

    @Test
    public void testConstructor_whenNullMessageAndNullThrowable_thenNullsGettable() {
        BootOpsException exception = new BootOpsException(null, null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}