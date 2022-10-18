package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * The ItemValidationResult encapsulates the information pertaining to the result of
 * a validation process executed on the item.  Failed validations take the form of a
 * ConstraintViolation<Item>.
 */
public interface ItemValidationResult {

    /**
     * Check to see if the Item was valid.
     * @return True if no ConstraintViolations occurred, otherwise false.
     */
    boolean isValid();

    /**
     * Add the provided set of ConstraintViolations to the result.
     * @param violations A set of ConstraintViolations that have occurred on the Item.
     */
    void addViolations(Set<ConstraintViolation<Item>> violations);

    /**
     * Add the provided set of ConstraintViolations to the result.
     * @param violation A ConstraintViolation that has occurred on the Item.
     */
    void addViolation(ConstraintViolation<Item> violation);

    /**
     * Retrieve the set of ConstraintViolations that occurred on the Item.
     * @return The set of ConstraintViolations.
     */
    Set<ConstraintViolation<Item>> getViolations();
}
