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
