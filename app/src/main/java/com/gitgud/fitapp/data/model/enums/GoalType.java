package com.gitgud.fitapp.data.model.enums;

public enum GoalType {
    RUNNING("RUNNING"),
    WEIGHT("WEIGHT"),
    TIME("TIME");

    private String type;

    GoalType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return type;
    }
}
