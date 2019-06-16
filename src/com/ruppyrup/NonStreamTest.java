package com.ruppyrup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NonStreamTest {

    SplitLifeCycles splitLifeCycles;

    @BeforeEach
    public void setup() {
        List<Event> auths1 = Arrays.asList(new Authorisation(1),
                new Authorisation(2),
                new Authorisation(3));

        List<Event> settle1 = Arrays.asList(new Settlement(1, 111),
                new Settlement(2, 112),
                new Settlement(3, 113));

        List<Event> auths2 = Arrays.asList(new Authorisation(11),
                new Authorisation(12),
                new Authorisation(13));

        List<Event> settle2 = Arrays.asList(new Settlement(11,116),
                new Settlement(12, 114),
                new Settlement(13, 115));

        List<Lifecycle> lifecycles = Arrays.asList(new Lifecycle(auths1, settle1, null),
                new Lifecycle(auths2, settle2, null));

        splitLifeCycles = new SplitLifeCycles(lifecycles);

    }

    @Test
    void testLifecycle() {

        List<Lifecycle> newLifecycles = splitLifeCycles.split();

        assertEquals(newLifecycles.size(), 6);
        for (Lifecycle lc : newLifecycles) {
            assertEquals(lc.getAuthEvents().get(0).getMti(), lc.getSettlementEvents().get(0).getMti());
        }
    }
}