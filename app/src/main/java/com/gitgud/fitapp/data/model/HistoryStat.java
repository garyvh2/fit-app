package com.gitgud.fitapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gitgud.fitapp.entities.user.LoginUserQuery;

@Entity(tableName = "history_stat")
public class HistoryStat {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private  Double imc;
    private Double height;
    private  Double weight;
    private  long userId;

    public HistoryStat(Double imc, Double height, Double weight) {
        this.imc = imc;
        this.height = height;
        this.weight = weight;
    }

    public HistoryStat(LoginUserQuery.HistoryStat stat) {
        imc = stat.imc();
        height = stat.height();
        weight = stat.weight();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
