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
package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DefaultItemValidationResultTest {

    @Test
    public void testIsValid_whenViolations_thenReturnFalse() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        HashSet<ConstraintViolation<Item>> set = new HashSet<>();
        set.add(Mockito.mock(ConstraintViolation.class));
        result.addViolations(set);

        assertFalse(result.isValid());
    }

    @Test
    public void testIsValid_whenViolation_thenReturnFalse() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        result.addViolation(Mockito.mock(ConstraintViolation.class));

        assertFalse(result.isValid());
    }

    @Test
    public void testIsValid_whenNoViolationsAdded_thenReturnTrue() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        assertTrue(result.isValid());
    }

    @Test
    public void testAddViolations_whenNullViolations_thenThrowIllegalArgumentException() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            result.addViolations(null);
        });

        Assertions.assertEquals("The Set of ConstraintViolations provided was null", thrown.getMessage());
    }

    @Test
    public void testAddViolations_whenViolations_thenAddAllViolations() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        HashSet<ConstraintViolation<Item>> set = new HashSet<>();
        ConstraintViolation violation = Mockito.mock(ConstraintViolation.class);
        set.add(violation);
        result.addViolations(set);

        assertEquals(1, result.getViolations().size());
        assertSame(violation, result.getViolations().toArray()[0]);
    }

    @Test
    public void testAddViolation_whenNullViolation_thenThrowIllegalArgumentException() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            result.addViolation(null);
        });

        Assertions.assertEquals("The ConstraintViolation provided was null", thrown.getMessage());
    }

    @Test
    public void testAddViolation_whenViolation_thenAddViolation() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        ConstraintViolation violation = Mockito.mock(ConstraintViolation.class);
        result.addViolation(violation);

        assertEquals(1, result.getViolations().size());
        assertSame(violation, result.getViolations().toArray()[0]);
    }

    @Test
    public void testGetViolations_whenNoViolations_thenReturnEmptySet() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        assertEquals(0, result.getViolations().size());
    }

    @Test
    public void testGetViolations_whenViolations_thenReturnAllViolations() {
        DefaultItemValidationResult result = new DefaultItemValidationResult();

        HashSet<ConstraintViolation<Item>> set = new HashSet<>();
        ConstraintViolation violation1 = Mockito.mock(ConstraintViolation.class);
        set.add(violation1);
        result.addViolations(set);

        ConstraintViolation violation2 = Mockito.mock(ConstraintViolation.class);
        result.addViolation(violation2);

        assertEquals(2, result.getViolations().size());
        assertTrue(result.getViolations().contains(violation1));
        assertTrue(result.getViolations().contains(violation2));
    }

}