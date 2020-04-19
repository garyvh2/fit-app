package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String description;
    private String type;
    private String image;
    private String tutorial;
    private  Float caloriesBurned;
    private int repetitions;
    private int time;
    private long routineId;

    public Exercise(long id, String name, String description, String type, String image, String tutorial, Float caloriesBurned, int time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.image = image;
        this.tutorial = tutorial;
        this.caloriesBurned = caloriesBurned;
        this.time = time;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTutorial() {
        return tutorial;
    }

    public void setTutorial(String tutorial) {
        this.tutorial = tutorial;
    }

    public Float getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Float caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getRoutineId() {
        return routineId;
    }

    public void setRoutineId(long routineId) {
        this.routineId = routineId;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
}
