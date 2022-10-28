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

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A ItemStreamCommandResult defines the required information that must be communicated back from processing of an Item
 * Stream by a command.
 */
@Getter
@Setter
public class ItemStreamCommandResult {
    private final List<ItemCommandResult> results = new ArrayList<>();

    /**
     * Add the result from processing of an Item by a command.
     * @param result The result from processing of an Item by a command.
     */
    public void addResult(ItemCommandResult result) {
        this.results.add(result);
    }

    /**
     * Check to see if the result was sucessful.
     * @return True if all of the processing was successful, otherwise false.
     */
    public boolean isSuccessful() {
        return results.isEmpty() || results.stream().allMatch(ItemCommandResult::isSuccessful);
    }

}
