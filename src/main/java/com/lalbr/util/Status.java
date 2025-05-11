package com.lalbr.util;

public enum Status {
    //Higher = more urgent
    OPEN(3),
    ONHOLD(2),
    CLOSED(1);


    private final int order;
    Status(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }
}
