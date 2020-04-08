package com.gitgud.fitapp.ui.modules.steps;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.model.StepsRecord;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.ActivityRepository;
import com.gitgud.fitapp.data.respository.StepsRepository;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.utils.DateUtils;

import java.util.List;

public class StepsViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private StepsRepository stepsRepository;
    private ActivityRepository activityRepository;

    private LiveData<User> currentUserLiveData;
    private LiveData<StepsRecord> todayStepsRecordLiveData;
    private LiveData<ActivityRecord> activeActivityLiveData;
    private LiveData<List<ActivityRecord>> todayActivityRecordsLiveData;

    public StepsViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        stepsRepository = new StepsRepository((application));
        activityRepository = new ActivityRepository(application);
        activeActivityLiveData = activityRepository.findActivityRecordByActiveAndTime(true, DateUtils.minDate(), DateUtils.maxDate());
        currentUserLiveData = userRepository.getCurrentUser();
        todayStepsRecordLiveData = stepsRepository.findStepsRecordByDate(DateUtils.minDate(), DateUtils.maxDate());
        todayActivityRecordsLiveData = activityRepository.findActivityRecordsByTime(DateUtils.minDate(), DateUtils.maxDate());
    }

    public LiveData<StepsRecord> getTodayStepsRecord() {
        return todayStepsRecordLiveData;
    }

    public LiveData<User> getCurrentUser() { return currentUserLiveData; }

    public LiveData<ActivityRecord> getActiveActivity() { return activeActivityLiveData; }

    public LiveData<List<ActivityRecord>> getTodayActivityRecords() { return todayActivityRecordsLiveData; }

    public void updateTodaySteps(StepsRecord stepsRecord, int steps) {
        stepsRepository.updateTodaySteps(stepsRecord, steps);
    }

    public void insertActivity (ActivityRecord activityRecord) {
        activityRepository.insert(activityRecord);
    }

}
