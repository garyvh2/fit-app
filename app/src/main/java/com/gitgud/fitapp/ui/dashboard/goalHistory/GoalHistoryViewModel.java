package com.gitgud.fitapp.ui.dashboard.goalHistory;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.RoutineRepository;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class GoalHistoryViewModel extends AndroidViewModel {
    GoalsRepository goalsRepository;
    LiveData<List<Goal>> goalList;

    public GoalHistoryViewModel(@NonNull Application application) {
        super(application);
        goalsRepository =  new GoalsRepository(application);
        goalList = goalsRepository.getInactiveGoals();

    }

    public LiveData<List<Goal>> getGoalList() {
        return goalList;
    }
}
