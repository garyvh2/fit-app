package com.gitgud.fitapp.ui.modules.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;

public class HistoryModel extends AndroidViewModel {

    // ATTRIBUTES START
    private int steps = 1500;
    private int calories = 2500;
    private Boolean loading;
    // ATTRIBUTES END


    public HistoryModel(@NonNull Application application) {
        super(application);
    }

    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
        setLoading(false);
    }

    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
        setLoading(false);
    }

    public Boolean getLoading() {
        return loading;
    }
    public void setLoading(Boolean loading) {
        this.loading = loading;
    }
}
