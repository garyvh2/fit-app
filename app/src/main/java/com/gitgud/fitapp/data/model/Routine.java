package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "routine")
public class Routine {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public  String name;
    public  String weekdays;

    public Routine(long id, String name, String weekdays) {
        this.id = id;
        this.name = name;
        this.weekdays = weekdays;
    }

    @Ignore
    public Routine(String name, String weekdays) {
        this.name = name;
        this.weekdays = weekdays;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }
}
