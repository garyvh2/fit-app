package com.gitgud.fitapp.data.model.enums;

public enum ActivityType {
    RUNNING("RUNNING"),
    WALKING("WALKING"),
    STILL("STILL"),
    ON_BICYCLE("ON_BICYCLE");

    private String type;

    ActivityType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return type;
    }
}
