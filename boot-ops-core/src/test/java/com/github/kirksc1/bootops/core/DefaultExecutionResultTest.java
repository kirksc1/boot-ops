package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultExecutionResultTest {

    @Test
    public void testConstructor_whenCreated_thenFailedWithNullFailureCause() {
        DefaultExecutionResult result = new DefaultExecutionResult();

        assertEquals(false, result.isSuccessful());
        assertNull(result.getFailureCause());
    }

    @Test
    public void testSucceeded_whenSucceeded_thenIsSuccessfulAndNullFailure() {
        DefaultExecutionResult result = new DefaultExecutionResult();

        result.succeeded();

        assertEquals(true, result.isSuccessful());
        assertNull(result.getFailureCause());
    }

    @Test
    public void testSucceeded_whenFailedThenSucceeded_thenIsSuccessfulAndNullFailure() {
        DefaultExecutionResult result = new DefaultExecutionResult();

        result.failed(new RuntimeException("Forced Failure"));
        result.succeeded();

        assertEquals(true, result.isSuccessful());
        assertNull(result.getFailureCause());
    }

    @Test
    public void testFailed_whenFailed_thenIsNotSuccessfulAndFailure() {
        DefaultExecutionResult result = new DefaultExecutionResult();

        Throwable t = new RuntimeException("Forced Failure");

        result.failed(t);

        assertEquals(false, result.isSuccessful());
        assertSame(t, result.getFailureCause());
    }

    @Test
    public void testFailed_whenSucceededThenFailed_thenIsNotSuccessfulAndFailure() {
        DefaultExecutionResult result = new DefaultExecutionResult();

        Throwable t = new RuntimeException("Forced Failure");

        result.succeeded();
        result.failed(t);

        assertEquals(false, result.isSuccessful());
        assertSame(t, result.getFailureCause());
    }

}