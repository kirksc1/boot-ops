package com.github.kirksc1.bootops.core.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class SystemCompletedEventTest {

    @Test
    public void testConstructor_whenConstructed_thenGettersReadable() {
        SystemCompletedEvent event = new SystemCompletedEvent(this);

        assertSame(this, event.getSource());
    }

}