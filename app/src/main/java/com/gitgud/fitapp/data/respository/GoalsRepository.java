package com.gitgud.fitapp.data.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.dao.GoalsDao;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.provider.database.AppDatabase;

import java.util.ArrayList;

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

    public LiveData<Goal> getCurrentGoal() { return goalsDao.getCurrentGoal(); }

    public  LiveData<ArrayList<Goal>> getAllGoals() {return  goalsDao.getAllGoals();}
}
