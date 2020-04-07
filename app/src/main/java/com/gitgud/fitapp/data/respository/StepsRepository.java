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

    public LiveData<StepsRecord> findStepsRecordByDate(Date start, Date end) {
        MediatorLiveData<StepsRecord> stepsRecordMediator = new MediatorLiveData<>();
        stepsRecordMediator.addSource(stepsRecordDao.findStepsRecordByDate(start, end), stepsRecord -> {
            if (stepsRecord == null) {
                StepsRecord newRecord = new StepsRecord(0, 6000, new Date());
                AsyncTask.execute(() -> stepsRecordDao.insert(newRecord));
                stepsRecordMediator.setValue(newRecord);
            } else {
                stepsRecordMediator.setValue(stepsRecord);
            }
        });
        return stepsRecordMediator;
    }

    public void updateTodaySteps(StepsRecord stepsRecord, int steps) {
        AsyncTask.execute(() -> {
            stepsRecord.setSteps(steps);
            stepsRecordDao.update(stepsRecord);
        });
    }
}
