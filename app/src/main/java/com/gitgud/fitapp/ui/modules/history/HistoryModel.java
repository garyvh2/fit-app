package com.gitgud.fitapp.ui.modules.history;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gitgud.fitapp.BR;

public class HistoryModel extends BaseObservable {

    // ATTRIBUTES START
    private int steps = 0;
    private int calories = 0;
    private Boolean loading;
    // ATTRIBUTES END

    public HistoryModel(@NonNull int steps, @NonNull int calories) {
        this.steps = steps;
        this.calories = calories;
    }

    @Bindable
    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
        setLoading(false);
        notifyPropertyChanged(BR.steps);
    }

    @Bindable
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
        setLoading(false);
        notifyPropertyChanged(BR.calories);
    }

    @Bindable
    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }
}
