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

/**
 * A ExecutionResult defines the required information that must be communicated back from processing.
 */
public interface ExecutionResult {

    /**
     * Check to see if the processing was completed successfully.
     * @return True if it was successful, otherwise false.
     */
    boolean isSuccessful();

    /**
     * Retrieve the cause of the failure if one occurred.
     * @return The cause of the failure if one occurred, otherwise null.
     */
    Throwable getFailureCause();
}
