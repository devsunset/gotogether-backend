package com.gotogether.system.enums;

public enum InvolveType {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE"),
    ONOFFLINE("ONOFFLINE");

    final private String name;

    private InvolveType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

