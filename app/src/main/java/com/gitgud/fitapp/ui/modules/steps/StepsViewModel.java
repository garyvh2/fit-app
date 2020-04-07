package com.gitgud.fitapp.ui.modules.steps;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingConversion;

import com.gitgud.fitapp.BR;

public class StepsViewModel extends BaseObservable {
    private int steps = 0;
    private String activity;

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
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
        notifyPropertyChanged(BR.activity);
    }
}
