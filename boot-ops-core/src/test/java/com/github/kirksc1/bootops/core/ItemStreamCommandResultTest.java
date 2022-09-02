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