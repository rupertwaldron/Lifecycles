package com.ruppyrup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpiltLifeCyclesWithStreams {

    private Set<Integer> getMtis(List<Lifecycle> lifecycles) {

        Stream<Integer> authMtis = lifecycles.stream()
                .filter(lifecycle -> lifecycle.getAuthEvents() != null)
                .flatMap(lifecycle -> lifecycle.getAuthEvents().stream())
                .map(Event::getMti);

        Stream<Integer> settleMtis = lifecycles.stream()
                .filter(lifecycle -> lifecycle.getSettlementEvents() != null)
                .flatMap(lifecycle -> lifecycle.getSettlementEvents().stream())
                .map(Event::getMti);

        return Stream.concat(authMtis, settleMtis).collect(Collectors.toSet());

    }

    private List<Event> getAuthEvents(List<Lifecycle> lifecycles) {
        return lifecycles.stream()
                .filter(lifecycle -> lifecycle.getAuthEvents() != null)
                .flatMap(lifecycle -> lifecycle.getAuthEvents().stream())
                .collect(Collectors.toList());
    }

    private List<Event> getSettleEvents(List<Lifecycle> lifecycles) {
        return lifecycles.stream()
                .filter(lifecycle -> lifecycle.getSettlementEvents() != null)
                .flatMap(lifecycle -> lifecycle.getSettlementEvents().stream())
                .collect(Collectors.toList());
    }

    private List<Event> getDisputeEvents(List<Lifecycle> lifecycles) {
        return lifecycles.stream()
                .filter(lifecycle -> lifecycle.getDisputes() != null)
                .flatMap(lifecycle -> lifecycle.getDisputes().stream())
                .collect(Collectors.toList());
    }

    public List<Lifecycle> split(List<Lifecycle> lifecycles) {
        List<Lifecycle> reconfiguredLifecycles = new ArrayList<>();
        Set<Integer> mtis = getMtis(lifecycles);
        List<Event> settleEvents = getSettleEvents(lifecycles);
        List<Event> authEvents = getAuthEvents(lifecycles);
        List<Event> disputeEvents = getDisputeEvents(lifecycles);

        for (Integer mti : mtis) {
            List<Event> myAuths = new ArrayList<>();
            List<Event> mySettles = new ArrayList<>();
            List<Event> myDisputes = new ArrayList<>();
            for (Event authEvent : authEvents) {
                if (authEvent.getMti() == mti)
                    myAuths.add(authEvent);
            }
            for (Event settleEvent : settleEvents) {
                if (settleEvent.getMti() == mti) {
                    mySettles.add(settleEvent);
                    for (Event dispute : disputeEvents) {
                        if (dispute.getGUID() == settleEvent.getGUID())
                            myDisputes.add(dispute);
                    }
                }
            }
            if (myAuths.size() > 0 || mySettles.size() > 0 || myDisputes.size() > 0)
                reconfiguredLifecycles.add(new Lifecycle(myAuths, mySettles, myDisputes));
        }
        return reconfiguredLifecycles;
    }


}
