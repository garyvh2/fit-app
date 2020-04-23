package com.gitgud.fitapp.ui.dashboard.dashboard.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.Routine;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.RoutineRepository;
import com.gitgud.fitapp.data.respository.UserRepository;

import org.jetbrains.annotations.NotNull;

public class DashboardViewModel extends AndroidViewModel {
    private  UserRepository userRepository;
    private  GoalsRepository goalsRepository;
    private RoutineRepository routineRepository;
    private LiveData<User> loggedUser;
    private LiveData<Goal> currentGoal;
    private MutableLiveData<Boolean> haveGoals;

    public DashboardViewModel(@NotNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        goalsRepository = new GoalsRepository(application);
        loggedUser = userRepository.getCurrentUser();
        currentGoal = goalsRepository.getCurrentGoal();
        routineRepository = new RoutineRepository(application);
        haveGoals = new MutableLiveData<>();
        haveGoals.postValue(false);

    }

    public MutableLiveData<Boolean> getHaveGoals() {
        return haveGoals;
    }

    public LiveData<User> getLoggedUser() {
        return loggedUser;
    }

    public LiveData<Goal> getCurrentGoal() {
        return currentGoal;
    }
    public void setHaveGoals(Boolean isGoals) {
        haveGoals.postValue(isGoals);
    }

    public  String getUserName() {
        User user = loggedUser.getValue();
        if(user == null) {
            return  "Welcome";
        }
        return "Welcome " + loggedUser.getValue().getName();
    }

    public LiveData<Routine> getTodayRoutine(String day) {
        return routineRepository.getTodayRoutine(day.toLowerCase());
    }
}
    // ATTRIBUTES START




