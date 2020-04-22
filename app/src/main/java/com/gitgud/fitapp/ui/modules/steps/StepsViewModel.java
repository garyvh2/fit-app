package com.gitgud.fitapp.ui.modules.steps;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.ActivityRecord;
import com.gitgud.fitapp.data.model.StepsRecord;
import com.gitgud.fitapp.data.respository.ActivityRepository;
import com.gitgud.fitapp.data.respository.StepsRepository;
import com.gitgud.fitapp.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StepsViewModel extends AndroidViewModel {
    private StepsRepository stepsRepository;
    private ActivityRepository activityRepository;

    private MutableLiveData<StepsRecord> todayStepsRecordLiveData;
    private LiveData<List<ActivityRecord>> todayActivityRecordsLiveData;

    private Date selectedDate = new Date();

    private Date start = DateUtils.minDate();
    private Date end = DateUtils.maxDate();

    public StepsViewModel(@NonNull Application application) {
        super(application);
        stepsRepository = new StepsRepository((application));
        activityRepository = new ActivityRepository(application);
        todayStepsRecordLiveData = stepsRepository.findStepsRecordByDate(start, end);
        todayActivityRecordsLiveData = activityRepository.findActivityRecordsByTime(start, end);
    }

    public MutableLiveData<StepsRecord> getTodayStepsRecord() {
        return todayStepsRecordLiveData;
    }
    public LiveData<List<ActivityRecord>> getTodayActivityRecords() { return todayActivityRecordsLiveData; }

    public void updateSteps(int goal) {
        stepsRepository.updateTodayGoal(goal);
    }

    public String getSelectedDate() {
        SimpleDateFormat dest = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return dest.format(selectedDate);
    }

    public void setSelectedDate(String string) {
        try {
            SimpleDateFormat dest = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            selectedDate = dest.parse(string);
        } catch (ParseException e) {
            Log.e("StepsViewModel", e.getMessage());
        }
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }
}
