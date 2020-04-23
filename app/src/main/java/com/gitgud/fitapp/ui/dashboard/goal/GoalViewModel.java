package com.gitgud.fitapp.ui.dashboard.goal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.respository.GoalsRepository;

public class GoalViewModel extends AndroidViewModel {
    private GoalsRepository goalsRepository;
    private LiveData<Goal> currentGoal;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        goalsRepository = new GoalsRepository(application);
        currentGoal = goalsRepository.getCurrentGoal();
    }
    public LiveData<Goal> getCurrentGoal() {
        return currentGoal;
    }

}
