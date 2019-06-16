package com.ruppyrup;

class Authorisation implements Event {
    Integer mti;

    public Authorisation(Integer mti) {
        this.mti = mti;
    }

    @Override
    public Integer getMti() {
        return mti;
    }

    @Override
    public Integer getGUID() {
        return null;
    }

    @Override
    public String toString() {
        return "Auth Event with mti : " + mti;
    }
}
