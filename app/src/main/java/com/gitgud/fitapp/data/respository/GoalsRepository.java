package com.gitgud.fitapp.data.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.gitgud.fitapp.data.dao.GoalsDao;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.provider.database.AppDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoalsRepository {
    private GoalsDao goalsDao;

    public GoalsRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        goalsDao = appDatabase.goalsDao();
    }

    public void insert(Goal goal) {
        AsyncTask.execute(() -> goalsDao.insert(goal));
    }

    public void update(Goal goal) {
        AsyncTask.execute(() -> goalsDao.update(goal));
    }

    public void delete(Goal goal) {
        AsyncTask.execute(() -> goalsDao.delete(goal));
    }

    public void insertAll(List<Goal> goalList) {AsyncTask.execute(() -> goalsDao.insertAll(goalList));}

    public LiveData<Goal> getCurrentGoal() { return goalsDao.getCurrentGoal(); }

    public  LiveData<List<Goal>> getAllGoals() {return  goalsDao.getAllGoals();}
}
