package com.gitgud.fitapp.ui.dashboard.goal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.respository.GoalsRepository;

public class GoalViewModel extends AndroidViewModel {
    private GoalsRepository goalsRepository;


    public GoalViewModel(@NonNull Application application) {
        super(application);
        goalsRepository = new GoalsRepository(application);
    }

    public void createGoal (Goal goal) {
        goalsRepository.insert(goal);
    }
}
