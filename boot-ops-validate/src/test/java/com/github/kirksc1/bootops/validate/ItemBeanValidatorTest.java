package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

class ItemBeanValidatorTest {

    private Validator validator = mock(Validator.class);
    private Item item = mock(Item.class);
    private ItemValidationResult result = mock(ItemValidationResult.class);

    private ItemBeanValidator itemBeanValidator;

    @BeforeEach
    public void beforeEach() {
        reset(validator, item, result);

        itemBeanValidator = new ItemBeanValidator(validator);
    }

    @Test
    public void testConstructor_whenValdiatorNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemBeanValidator( null);
        });

        Assertions.assertEquals("The Validator provided was null", thrown.getMessage());
    }

    @Test
    public void testValidate_whenItemNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemBeanValidator(validator).validate(null, result);
        });

        Assertions.assertEquals("The Item provided was null", thrown.getMessage());
    }

    @Test
    public void testValidate_whenResultNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemBeanValidator(validator).validate(item, null);
        });

        Assertions.assertEquals("The ItemValidationResult provided was null", thrown.getMessage());
    }

    @Test
    public void testValidate_whenNoViolations_thenDoNothing() {
        Set<ConstraintViolation<Item>> violations = new HashSet<>();

        when(validator.validate(item)).thenReturn(violations);

        itemBeanValidator.validate(item, result);

        verifyNoInteractions(result);
    }

    @Test
    public void testValidate_whenNoViolations_thenAddViolationsToResult() {
        Set<ConstraintViolation<Item>> violations = new HashSet<>();
        ConstraintViolation<Item> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<Item> violation2 = mock(ConstraintViolation.class);

        violations.add(violation1);
        violations.add(violation2);

        when(validator.validate(item)).thenReturn(violations);

        itemBeanValidator.validate(item, result);

        verify(result, times(1)).addViolations(same(violations));
    }

}