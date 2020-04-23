package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gitgud.fitapp.data.model.enums.GoalType;
import com.gitgud.fitapp.data.model.enums.Status;
import com.gitgud.fitapp.entities.user.LoginUserQuery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public  Goal (LoginUserQuery.Goal goal) {
        this.name = goal.name();
        this.date = goal.limitDate();
        this.goal = goal.objective().intValue();
        this.progress = goal.current().intValue();
        this.dbId = goal._id();
        goalType = goal.type();
        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate lDate = LocalDate.parse(date, oldFormatter);
        this.status = lDate.compareTo(LocalDate.now()) > 0 && this.goal != progress ?
                Status.ACTIVE.getUrl() :
                Status.INACTIVE.getUrl();
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String date;
    private int goal;
    private int progress;
    private String status;
    private String goalType;
    private String dbId;

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
        if(progress == goal) {
            status = Status.INACTIVE.getUrl();
        }
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

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
