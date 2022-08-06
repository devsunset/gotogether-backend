package com.gotogether.system.enums;

public enum SkillLevelType {

    BASIC("BASIC"),
    JOB("JOB"),
    INTEREST("INTEREST"),
    TOY_PROJECT("TOY_PROJECT");

    final private String name;

    private SkillLevelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

