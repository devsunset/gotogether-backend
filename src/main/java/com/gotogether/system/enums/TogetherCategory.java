package com.gotogether.system.enums;

public enum TogetherCategory {
    STUDY("STUDY"),
    PORTFOLIO("PORTFOLIO"),
    HACKATHON("HACKATHON"),
    CONTEST("CONTEST"),
    TOY_PROJECT("TOY_PROJECT"),
    PROJECT("PROJECT"),
    ETC("ETC");

    final private String name;

    private TogetherCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

