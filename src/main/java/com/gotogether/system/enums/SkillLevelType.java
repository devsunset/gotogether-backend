package com.gotogether.system.enums;

public enum SkillLevelType {
    INTEREST("INTEREST"),
    HELLO_WORLD("HELLO_WORLD"),
    BASIC("BASIC"),
    TOY_PROJECT("TOY_PROJECT"),
    JOB_BEGINNER("JOB_BEGINNER"),
    JOB_PROFESSIONAL("JOB_PROFESSIONAL");

    final private String name;
    public String getName() {
        return name;
    }
    private SkillLevelType(String name){
        this.name = name;
    }
}

