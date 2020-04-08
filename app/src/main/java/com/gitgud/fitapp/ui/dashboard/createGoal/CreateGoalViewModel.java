package com.gitgud.fitapp.ui.dashboard.createGoal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.respository.GoalsRepository;

public class CreateGoalViewModel extends AndroidViewModel {
    private GoalsRepository goalsRepository;


    public CreateGoalViewModel(@NonNull Application application) {
        super(application);
        goalsRepository = new GoalsRepository(application);
    }

    public void createGoal (Goal goal) {
        goalsRepository.insert(goal);
    }
}
