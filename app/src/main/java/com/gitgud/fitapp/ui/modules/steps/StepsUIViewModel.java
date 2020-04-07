package com.gitgud.fitapp.ui.modules.steps;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class StepsUIViewModel extends BaseObservable {
    private String activity = "STILL";

    public StepsUIViewModel() {
    }

    @Bindable
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
}
