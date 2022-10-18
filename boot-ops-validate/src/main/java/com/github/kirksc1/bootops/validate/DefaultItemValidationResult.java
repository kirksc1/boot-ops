package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.Item;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

/**
 * DefaultItemValidationResult is the default implementation of the ItemValidationResult
 * interface.
 */
public class DefaultItemValidationResult implements ItemValidationResult {

    private final Set<ConstraintViolation<Item>> violations = new HashSet<>();

    /**
     * @inheritDoc
     */
    @Override
    public boolean isValid() {
        return violations.isEmpty();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addViolations(Set<ConstraintViolation<Item>> violations) {
        Assert.notNull(violations, "The Set of ConstraintViolations provided was null");

        this.violations.addAll(violations);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addViolation(ConstraintViolation<Item> violation) {
        Assert.notNull(violation, "The ConstraintViolation provided was null");

        this.violations.add(violation);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<ConstraintViolation<Item>> getViolations() {
        return violations;
    }
}
