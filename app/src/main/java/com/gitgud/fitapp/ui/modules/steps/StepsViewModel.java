package com.gitgud.fitapp.ui.modules.steps;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingConversion;

import com.gitgud.fitapp.BR;

public class StepsViewModel extends BaseObservable {
    private int steps = 140;
    private int takenSteps = 140;
    private int recordedSteps = 0;
    private int goal = 250;

    private String activity = "STILL";

    public StepsViewModel() {
    }

    @Bindable
    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
        notifyPropertyChanged(BR.steps);
    }
    @Bindable
    public int getTakenSteps() { return takenSteps; }
    public void setTakenSteps(int takenSteps) {
        this.takenSteps = takenSteps;
        notifyPropertyChanged(BR.takenSteps);
    }

    @Bindable
    public int getRecordedSteps() { return recordedSteps; }
    public void setRecordedSteps(int recordedSteps) {
        this.recordedSteps = recordedSteps;
        notifyPropertyChanged(BR.recordedSteps);
    }

    @Bindable
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
        notifyPropertyChanged(BR.activity);
    }

    @Bindable
    public int getGoal() { return goal; }
    public void setGoal(int goal) {
        this.goal = goal;
        notifyPropertyChanged(BR.goal);
    }
}
