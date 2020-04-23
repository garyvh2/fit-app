package com.gitgud.fitapp.ui.dashboard.createGoal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.GoalDataSource;
import com.gitgud.fitapp.entities.user.AddUserGoalMutation;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateGoalViewModel extends AndroidViewModel {
    private GoalsRepository goalsRepository;
    private UserRepository userRepository;
    private GoalDataSource goalDataSource;
    private LiveData<User> currentUser;


    public CreateGoalViewModel(@NonNull Application application) {
        super(application);
        goalsRepository = new GoalsRepository(application);
        userRepository = new UserRepository(application);
        goalDataSource = GoalDataSource.getInstance();
        currentUser = userRepository.getCurrentUser();
    }

    public void createLocalGoal (String goalId, Goal goal) {
        goal.setDbId(goalId);
        goalsRepository.insert(goal);
    }

    public Observable<AddUserGoalMutation.Data> createGoal (String userId, Goal goal){

        return goalDataSource.postGoal(userId, goal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }
}
