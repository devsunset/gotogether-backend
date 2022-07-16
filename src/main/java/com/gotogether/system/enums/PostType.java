package com.gotogether.system.enums;

public enum PostType {
    TALK("TALK"),
    QA("QA");

    final private String name;
    public String getName() {
        return name;
    }
    private PostType(String name){
        this.name = name;
    }
}

