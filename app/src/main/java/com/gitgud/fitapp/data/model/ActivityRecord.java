package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "activity_record_table")
public class ActivityRecord {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String type;
    private Date date;
    private long timeElapsed;
    private boolean active;

    public ActivityRecord(String type, Date date, long timeElapsed, boolean active) {
        this.type = type;
        this.date = date;
        this.timeElapsed = timeElapsed;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
