package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "water_record_table")
public class WaterRecord {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int quantity;
    private int goal;
    private Date date;

    public WaterRecord(int quantity, int goal, Date date) {
        this.quantity = quantity;
        this.goal = goal;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
