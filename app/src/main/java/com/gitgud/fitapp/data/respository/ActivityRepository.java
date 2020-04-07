package com.gitgud.fitapp.data.respository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.dao.ActivityRecordDao;
import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.model.StepsRecord;
import com.gitgud.fitapp.provider.database.AppDatabase;
import com.gitgud.fitapp.utils.DateUtils;

import java.util.Date;

public class ActivityRepository {
    private ActivityRecordDao activityRecordDao;

    public ActivityRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        activityRecordDao = appDatabase.activityRecordDao();
    }

    public void insert(ActivityRecord activityRecord) {
        AsyncTask.execute(() -> activityRecordDao.insert(activityRecord));
    }

    public void update(ActivityRecord activityRecord) {
        AsyncTask.execute(() -> activityRecordDao.update(activityRecord));
    }

    public void delete(ActivityRecord activityRecord) {
        AsyncTask.execute(() -> activityRecordDao.delete(activityRecord));
    }

    public void transitionActivity(String type) {
        AsyncTask.execute(() -> {
            ActivityRecord currentRecord = activityRecordDao.findActivityRecordByActiveAndTimeSync(true, DateUtils.minDate(), DateUtils.maxDate());

            if (currentRecord == null) {
                activityRecordDao.insert(new ActivityRecord(type, new Date(), 0, true));
            } else {
                /**
                 * Calculate elapsed time and stop activity
                 */
                currentRecord.setTimeElapsed(currentRecord.getTimeElapsed() + new Date().getTime() - currentRecord.getDate().getTime());
                currentRecord.setActive(false);
                activityRecordDao.update(currentRecord);
                /**
                 * Initialize the next activity
                 */
                ActivityRecord nextRecord = activityRecordDao.findActivityRecordByTypeAndTimeSync(type, DateUtils.minDate(), DateUtils.maxDate());
                if (nextRecord == null) {
                    activityRecordDao.insert(new ActivityRecord(type, new Date(), 0, true));
                } else {
                    nextRecord.setDate(new Date());
                    nextRecord.setActive(true);
                    activityRecordDao.update(nextRecord);
                }
            }
        });
    }

    public LiveData<ActivityRecord> findActivityRecordByActiveAndTime(boolean active, Date from, Date to) {
        return activityRecordDao.findActivityRecordByActiveAndTime(active, from, to);
    }

    public LiveData<ActivityRecord> findActivityRecordByTypeAndTime(String type, Date from, Date to) {
        return activityRecordDao.findActivityRecordByTypeAndTime(type, from, to);
    }
}
