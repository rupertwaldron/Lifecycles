package com.ruppyrup;

class Settlement implements Event {
    Integer mti;
    Integer GUID;

public Settlement(Integer mti, Integer GUID) {
        this.mti = mti;
        this.GUID = GUID;
    }

    @Override
    public Integer getMti() {
        return mti;
    }

    @Override
    public Integer getGUID() {
        return GUID;
    }

    @Override
    public String toString() {
        return "Settlement Event with mti : " + mti +
                "and GUID : " + GUID;
    }
}
