package com.gitgud.fitapp.ui.dashboard.dashboard.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.UserRepository;

import org.jetbrains.annotations.NotNull;

public class DashboardViewModel extends AndroidViewModel {
    UserRepository userRepository;
    GoalsRepository goalsRepository;
    LiveData<User> loggedUser;
    LiveData<Goal> currentGoal;
    private MutableLiveData<Boolean> haveGoals;

    DashboardViewModel(@NotNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        goalsRepository = new GoalsRepository(application);
        loggedUser = userRepository.getCurrentUser();
        currentGoal = goalsRepository.getCurrentGoal();
        setHaveGoals(currentGoal != null);
    }

    public MutableLiveData<Boolean> getHaveGoals() {
        return haveGoals;
    }

    public void setHaveGoals(Boolean haveGoals) {
        this.haveGoals.postValue(haveGoals);
    }

    public  String getUserName() {
        return loggedUser.getValue().getName();
    }
}
    // ATTRIBUTES START




