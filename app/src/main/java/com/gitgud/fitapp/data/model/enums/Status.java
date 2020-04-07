package com.gitgud.fitapp.data.model.enums;

public enum Status {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String type;

    Status(String type) {
        this.type = type;
    }

    public String getUrl() {
        return type;
    }
}
