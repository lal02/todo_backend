package com.lalbr.util;

public enum Priority {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private final int order;
    Priority(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }
}
