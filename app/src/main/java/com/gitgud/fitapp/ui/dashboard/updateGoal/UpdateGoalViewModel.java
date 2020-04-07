package com.gitgud.fitapp.ui.dashboard.updateGoal;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gitgud.fitapp.BR;

public class UpdateGoalViewModel extends BaseObservable {

    private Integer goalsCounter = 0;

    private Integer progress = 10;

    public UpdateGoalViewModel(){

    }

    @Bindable
    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
        notifyPropertyChanged(BR.progress);
    }

    @Bindable
    public Integer getGoalsCounter() {
        return goalsCounter;
    }

    public void setGoalsCounter(Integer goalsCounter) {
        this.goalsCounter = goalsCounter;
        notifyPropertyChanged(BR.goalsCounter);
    }
}
