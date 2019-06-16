package com.ruppyrup;

import java.util.List;

class Lifecycle {
    private final List<Event> authEvents;
    private final List<Event> settlementEvents;
    private final List<Event> disputes;

    public Lifecycle(List<Event> authEvents, List<Event> settlementEvents, List<Event> disputes) {
        this.authEvents = authEvents;
        this.settlementEvents = settlementEvents;
        this.disputes = disputes;
    }

    public List<Event> getSettlementEvents() {
        return this.settlementEvents;
    }

    public List<Event> getAuthEvents() {
        return this.authEvents;
    }

    public List<Event> getDisputes() {
        return this.disputes;
    }

    @Override
    public String toString() {
        String authMtis = "";
        for (Event authEvent : authEvents) {
            authMtis += authEvent.getMti() + " : ";
        }
        String settleMtisGuids = "";
        for (Event settleEvent : settlementEvents) {
            settleMtisGuids += settleEvent.getMti() + "/" + settleEvent.getGUID() + " : ";
        }
        String disputeGuids = "";
        for (Event dispute: disputes) {
            disputeGuids += dispute.getGUID() + " : ";
        }

        return "------------------------------------------------\n" +
                "Lifecycle - Auth mti = " + authMtis + "\n"
                + "Settle mti and GUID = " + settleMtisGuids + "\n"
                + "Dispute GUID = " + disputeGuids + "\n"
                + "------------------------------------------------";
    }
}
