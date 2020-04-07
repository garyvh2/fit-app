package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "steps_record_table")
public class StepsRecord {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int steps;
    private int goal;
    private Date date;

    public StepsRecord(int steps, int goal, Date date) {
        this.steps = steps;
        this.goal = goal;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
