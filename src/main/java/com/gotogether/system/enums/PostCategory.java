package com.gotogether.system.enums;

public enum PostCategory {
    TALK("TALK"),
    QA("QA");

    final private String name;

    private PostCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

