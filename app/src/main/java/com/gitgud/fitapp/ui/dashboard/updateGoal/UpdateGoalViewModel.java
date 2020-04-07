package com.gitgud.fitapp.ui.dashboard.updateGoal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class UpdateGoalViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> goalsCounter;
    private MutableLiveData<Integer> progress;

    public UpdateGoalViewModel(@NonNull Application application) {
        super(application);
        progress = new MutableLiveData<>();
        progress.postValue(0);
        goalsCounter = new MutableLiveData<>();
        goalsCounter.postValue(0);
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }
    public void setProgress(Integer progress) {
        this.progress.postValue(progress);
    }

    public LiveData<Integer> getGoalsCounter() {
        return goalsCounter;
    }
    public void setGoalsCounter(Integer goalsCounter) {
        this.goalsCounter.postValue(goalsCounter);
    }
}
