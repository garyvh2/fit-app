package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gitgud.fitapp.data.model.enums.GoalType;
import com.gitgud.fitapp.data.model.enums.Status;
@Entity(tableName = "goals")
public class Goal {

    public Goal(String name, String date, int goal, int progress, String status, String goalType) {
        this.name = name;
        this.date = date;
        this.goal = goal;
        this.progress = progress;
        this.status = status;
        this.goalType = goalType;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String date;
    private int goal;
    private int progress;
    private String status;
    private String goalType;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
