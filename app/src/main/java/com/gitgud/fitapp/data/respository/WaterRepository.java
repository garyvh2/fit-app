package com.gitgud.fitapp.data.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.gitgud.fitapp.data.dao.WaterRecordDao;
import com.gitgud.fitapp.data.model.WaterRecord;
import com.gitgud.fitapp.provider.database.AppDatabase;
import com.gitgud.fitapp.utils.DateUtils;

import java.util.Date;

public class WaterRepository {
    private WaterRecordDao waterRecordDao;

    public WaterRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        this.waterRecordDao = appDatabase.waterRecordDao();
    }

    public void insert(WaterRecord waterRecord) {
        AsyncTask.execute(() -> { this.waterRecordDao.insert(waterRecord); });
    }

    public void update(WaterRecord waterRecord) {
        AsyncTask.execute(() -> { this.waterRecordDao.update(waterRecord); });
    }

    public void delete(WaterRecord waterRecord) {
        AsyncTask.execute(() -> { this.waterRecordDao.delete(waterRecord); });
    }

    public LiveData<WaterRecord> findActivityRecordByTime (Date from, Date to) {
        MediatorLiveData<WaterRecord> mediator = new MediatorLiveData<>();
        mediator.addSource(this.waterRecordDao.findActivityRecordByTime(from, to), waterRecord -> {
            if (waterRecord == null) {
                WaterRecord newWaterRecord = new WaterRecord(0, 2500, new Date());
                AsyncTask.execute(() -> { this.waterRecordDao.insert(newWaterRecord); });
                mediator.postValue(newWaterRecord);
            } else {
                mediator.postValue(waterRecord);
            }
        });
        return mediator;
    }

    public void updateTodayWater(WaterRecord waterRecord, int quantity) {
        AsyncTask.execute(() -> {
            waterRecord.setQuantity(quantity);
            waterRecordDao.update(waterRecord);
        });
    }

    public void updateTodayGoal(int goal) {
        AsyncTask.execute(() -> {
            WaterRecord waterRecord = waterRecordDao.findActivityRecordByTimeSync(DateUtils.minDate(), DateUtils.maxDate());
            waterRecord.setGoal(goal);
            waterRecordDao.update(waterRecord);
        });
    }
}
