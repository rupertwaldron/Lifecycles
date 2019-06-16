package com.ruppyrup;

class Dispute implements Event {
    Integer GUID;

public Dispute(Integer GUID) {
        this.GUID = GUID;
    }

    public Integer getGUID() {
        return GUID;
    }

    @Override
    public Integer getMti() {
        return null;
    }

    @Override
    public String toString() {
        return "Dispute Event with GUID : " + GUID;
    }
}
