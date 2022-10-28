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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemStreamCommandResultTest {

    private ItemCommandResult result1 = mock(ItemCommandResult.class);
    private ItemCommandResult result2 = mock(ItemCommandResult.class);

    @BeforeEach
    public void before() {
        reset(result1, result2);
    }

    @Test
    public void testIsSuccessful_whenNoResults_thenReturnTrue() {
        ItemStreamCommandResult result = new ItemStreamCommandResult();

        assertTrue(result.isSuccessful());
    }

    @Test
    public void testIsSuccessful_whenAllResultsSuccessful_thenReturnTrue() {
        when(result1.isSuccessful()).thenReturn(true);

        ItemStreamCommandResult result = new ItemStreamCommandResult();
        result.addResult(result1);

        assertTrue(result.isSuccessful());
    }

    @Test
    public void testIsSuccessful_whenOneResultNotSuccessful_thenReturnFalse() {
        when(result1.isSuccessful()).thenReturn(false);

        ItemStreamCommandResult result = new ItemStreamCommandResult();
        result.addResult(result1);

        assertFalse(result.isSuccessful());
    }

    @Test
    public void testIsSuccessful_whenOneResultSuccessfuleAndOneResultNotSuccessful_thenReturnFalse() {
        when(result1.isSuccessful()).thenReturn(true);
        when(result2.isSuccessful()).thenReturn(false);

        ItemStreamCommandResult result = new ItemStreamCommandResult();
        result.addResult(result1);
        result.addResult(result2);

        assertFalse(result.isSuccessful());
    }

    @Test
    public void testIsSuccessful_whenMultipleResultsNotSuccessful_thenReturnFalse() {
        when(result1.isSuccessful()).thenReturn(false);
        when(result2.isSuccessful()).thenReturn(false);

        ItemStreamCommandResult result = new ItemStreamCommandResult();
        result.addResult(result1);
        result.addResult(result2);

        assertFalse(result.isSuccessful());
    }

}