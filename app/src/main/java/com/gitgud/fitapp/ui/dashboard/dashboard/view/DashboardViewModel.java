package com.gitgud.fitapp.ui.dashboard.dashboard.view;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class DashboardViewModel extends BaseObservable {

    DashboardViewModel(){

    }

    // ATTRIBUTES START
    private Boolean goals = true;


    @Bindable
    public Boolean getGoals() {
        return goals;
    }

    public void setGoals(Boolean goals) {
        this.goals = goals;
        notifyPropertyChanged(com.gitgud.fitapp.BR.goals);
    }
}
