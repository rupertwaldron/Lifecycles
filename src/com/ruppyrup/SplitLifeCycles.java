package com.ruppyrup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SplitLifeCycles {
    private List<Lifecycle> lifecycles;
    private List<Lifecycle> reconfiguredLifecycles;
    private List<Integer> mtis;

    public SplitLifeCycles(List<Lifecycle> lifecycles) {
        this.lifecycles = lifecycles;
        this.reconfiguredLifecycles = new ArrayList<>();
        this.mtis = new ArrayList<>();
    }

    private void getMtis() {
         mtis = lifecycles.stream()
                .flatMap(lifecycle -> lifecycle.getAuthEvents().stream())
                .map(Event::getMti)
                .collect(Collectors.toList());
    }

    public List<Lifecycle> split() {

        getMtis();

        for (Integer mti: mtis) {
            for (Lifecycle lifeCycle : lifecycles) {
                List<Event> myAuths = new ArrayList<>();
                List<Event> mySettles = new ArrayList<>();
                for (Event auth: lifeCycle.getAuthEvents()) {
                    if (auth.getMti() == mti)
                        myAuths.add(auth);
                }
                for (Event settle: lifeCycle.getSettlementEvents()) {
                    if (settle.getMti() == mti)
                        mySettles.add(settle);
                }

                if (myAuths.size() > 0 || mySettles.size() > 0)
                    reconfiguredLifecycles.add(new Lifecycle(myAuths, mySettles, null));
            }
        }

        return reconfiguredLifecycles;
    }
}
