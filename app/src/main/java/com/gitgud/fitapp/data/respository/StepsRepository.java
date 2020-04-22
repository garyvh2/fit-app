package com.gitgud.fitapp.data.respository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.dao.StepsRecordDao;
import com.gitgud.fitapp.data.dao.UserDao;
import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.model.StepsRecord;
import com.gitgud.fitapp.provider.database.AppDatabase;
import com.gitgud.fitapp.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class StepsRepository {
    private UserDao userDao;
    private StepsRecordDao stepsRecordDao;

    public StepsRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        userDao = appDatabase.userDao();
        stepsRecordDao = appDatabase.stepsRecordDao();
    }

    public void insert(StepsRecord stepsRecord) {
        AsyncTask.execute(() -> stepsRecordDao.insert(stepsRecord));
    }

    public void update(StepsRecord stepsRecord) {
        AsyncTask.execute(() -> stepsRecordDao.update(stepsRecord));
    }

    public void delete(StepsRecord stepsRecord) {
        AsyncTask.execute(() -> stepsRecordDao.delete(stepsRecord));
    }

    public void createDailySteps() {
        AsyncTask.execute(() -> {
            StepsRecord stepsRecord = stepsRecordDao.findStepsRecordByDateSync(DateUtils.minDate(), DateUtils.maxDate());
            if (stepsRecord == null) {
                StepsRecord newRecord = new StepsRecord(0, 6000, new Date());
                stepsRecordDao.insert(newRecord);
            }
        });
    }

    public MutableLiveData<StepsRecord> findStepsRecordByDate(Date start, Date end) {
        MediatorLiveData<StepsRecord> stepsRecordMediator = new MediatorLiveData<>();
        stepsRecordMediator.addSource(stepsRecordDao.findStepsRecordByDate(start, end), stepsRecord -> {
            if (stepsRecord == null) {
                StepsRecord newRecord = new StepsRecord(0, 6000, new Date());
                AsyncTask.execute(() -> stepsRecordDao.insert(newRecord));
                stepsRecordMediator.postValue(newRecord);
            } else {
                stepsRecordMediator.postValue(stepsRecord);
            }
        });
        return stepsRecordMediator;
    }

    public void updateTodaySteps(int steps) {
        AsyncTask.execute(() -> {
            StepsRecord stepsRecord = stepsRecordDao.findStepsRecordByDateSync(DateUtils.minDate(), DateUtils.maxDate());
            stepsRecord.setSteps(steps);
            stepsRecordDao.update(stepsRecord);
        });
    }

    public void updateTodayGoal(int goal) {
        AsyncTask.execute(() -> {
            StepsRecord stepsRecord = stepsRecordDao.findStepsRecordByDateSync(DateUtils.minDate(), DateUtils.maxDate());
            stepsRecord.setGoal(goal);
            stepsRecordDao.update(stepsRecord);
        });
    }

    public void addStep(Date start, Date end) {
        AsyncTask.execute(() -> {
            StepsRecord stepsRecord = stepsRecordDao.findStepsRecordByDateSync(start, end);
            stepsRecord.setSteps(stepsRecord.getSteps() + 1);
            stepsRecordDao.update(stepsRecord);
        });
    }
}
