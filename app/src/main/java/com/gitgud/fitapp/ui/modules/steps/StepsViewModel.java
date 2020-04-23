package com.gitgud.fitapp.ui.modules.steps;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.model.StepsRecord;
import com.gitgud.fitapp.data.respository.ActivityRepository;
import com.gitgud.fitapp.data.respository.StepsRepository;
import com.gitgud.fitapp.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class StepsViewModel extends AndroidViewModel {
    private StepsRepository stepsRepository;
    private ActivityRepository activityRepository;

    private LiveData<StepsRecord> todayStepsRecordLiveData;
    private LiveData<List<ActivityRecord>> todayActivityRecordsLiveData;

    public StepsViewModel(@NonNull Application application) {
        super(application);
        stepsRepository = new StepsRepository((application));
        activityRepository = new ActivityRepository(application);

        Date today = new Date();
        this.setTodayStepsRecord(today);
        this.setTodayActivityRecords(today);
    }

    public LiveData<StepsRecord> getTodayStepsRecord() {
        return todayStepsRecordLiveData;
    }
    public void setTodayStepsRecord(Date date) {
        todayStepsRecordLiveData = stepsRepository.findStepsRecordByDate(DateUtils.minDate(date), DateUtils.maxDate(date));
    }

    public LiveData<List<ActivityRecord>> getTodayActivityRecords() { return todayActivityRecordsLiveData; }
    public void setTodayActivityRecords(Date date) {
        todayActivityRecordsLiveData = activityRepository.findActivityRecordsByTime(DateUtils.minDate(date), DateUtils.maxDate(date));
    }

    public void updateSteps(int goal) {
        stepsRepository.updateTodayGoal(goal);
    }
}
