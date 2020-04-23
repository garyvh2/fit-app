package com.gitgud.fitapp.ui.dashboard.updateGoal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.GoalDataSource;
import com.gitgud.fitapp.entities.user.UpdateUserGoalMutation;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class UpdateGoalViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> goalsCounter;
    private MutableLiveData<Integer> progress;
    private GoalDataSource goalDataSource;
    private UserRepository userRepository;
    private GoalsRepository goalsRepository;
    private LiveData<Goal> currentGoal;
    private LiveData<User> currentUser;

    public UpdateGoalViewModel(@NonNull Application application) {
        super(application);
        progress = new MutableLiveData<>();
        goalsRepository = new GoalsRepository(application);
        userRepository = new UserRepository(application);
        currentGoal = goalsRepository.getCurrentGoal();
        goalsCounter = new MutableLiveData<>();
        currentUser = userRepository.getCurrentUser();
        goalDataSource =  GoalDataSource.getInstance();
        progress.postValue(0);
        goalsCounter.postValue(0);
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }
    public void setProgress(Integer progress) {
        this.progress.postValue(progress);
    }

    public LiveData<Integer> getGoalsCounter() {
        return goalsCounter;
    }
    public void setGoalsCounter(Integer goalsCounter) {
        this.goalsCounter.postValue(goalsCounter);
    }
    public LiveData<Goal> getCurrentGoal() {
        return currentGoal;
    }

    public void updateGoalCounter() {
        AsyncTask.execute(() -> {
            Goal goal = currentGoal.getValue();
            goal.setProgress(progress.getValue());
            goalsRepository.update(goal);
        });

    }

    public Observable<UpdateUserGoalMutation.Data> update (String userId, Goal goal){

        return goalDataSource.updateGoal(userId, goal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }
}
