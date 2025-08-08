package com.vlg.testick.model;

public enum Type {
    RADIO("radio"),
    CHECKBOX("checkbox"),
    TEXT("text");

    private String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
