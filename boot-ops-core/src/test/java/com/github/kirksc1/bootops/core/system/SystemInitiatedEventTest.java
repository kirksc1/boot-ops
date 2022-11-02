package com.github.kirksc1.bootops.core.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class SystemInitiatedEventTest {

    @Test
    public void testConstructor_whenConstructed_thenGettersReadable() {
        SystemInitiatedEvent event = new SystemInitiatedEvent(this);

        assertSame(this, event.getSource());
    }

}