package com.ruppyrup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamTest {

    List<Lifecycle> lifecycles;

    SpiltLifeCyclesWithStreams spiltLifeCyclesWithStreams;

    @BeforeEach
    public void setup() {
        List<Event> auths1 = Arrays.asList(new Authorisation(1),
                new Authorisation(2),
                new Authorisation(3));

        List<Event> settle1 = Arrays.asList(new Settlement(1, 111),
                new Settlement(2, 101),
                new Settlement(3, 102));

        List<Event> auths2 = Arrays.asList(new Authorisation(11),
                new Authorisation(12),
                new Authorisation(13));

        List<Event> settle2 = Arrays.asList(new Settlement(11, 112),
                new Settlement(12, 103),
                new Settlement(13,104));

        List<Event> settle3 = Arrays.asList(new Settlement(11, 113),
                new Settlement(12, 105),
                new Settlement(23,106));

        List<Event> disputes = Arrays.asList(new Dispute(103), new Dispute(106), new Dispute(106));

        lifecycles = Arrays.asList(new Lifecycle(auths1, settle1, null),
                new Lifecycle(auths2, settle2, disputes),
                new Lifecycle(null, settle3, null));

        spiltLifeCyclesWithStreams = new SpiltLifeCyclesWithStreams();

    }

    @Test
    void testLifecycle() {

        List<Lifecycle> newLifecycles = spiltLifeCyclesWithStreams.split(lifecycles);

        newLifecycles.forEach(System.out::println);

        assertEquals(newLifecycles.size(), 7);
        for (Lifecycle lc : newLifecycles) {
            if (!lc.getAuthEvents().isEmpty() && !lc.getSettlementEvents().isEmpty())
                assertEquals(lc.getAuthEvents().get(0).getMti(), lc.getSettlementEvents().get(0).getMti());
            else if (!lc.getDisputes().isEmpty())
                assertEquals(lc.getSettlementEvents().get(0).getGUID(), lc.getDisputes().get(0).getGUID());
            else
                assertEquals(lc.getSettlementEvents().size(), 1);
        }
    }
}