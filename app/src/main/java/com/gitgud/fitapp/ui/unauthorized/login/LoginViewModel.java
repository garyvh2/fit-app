package com.gitgud.fitapp.ui.unauthorized.login;

import android.app.Application;

import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.UserDataSource;
import com.gitgud.fitapp.entities.user.LoginUserQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    // INJECTION START
    @NonNull
    private UserDataSource userDataSource;
    private UserRepository userRepository;
    private GoalsRepository goalsRepository;
    // ATTRIBUTES START
    private Boolean loading;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        goalsRepository =  new GoalsRepository(application);
        this.userDataSource = UserDataSource.getInstance();
    }


    public Observable<LoginUserQuery.Data> loginObservable(@NotNull String email, @NonNull String password) {
        setLoading(true);
        return this.userDataSource
                .loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void saveLoggedUser(User user) {
        userRepository.insert(user);
    }

    public  void saveGoals(ArrayList<Goal> goals) {
        for (Goal goal: goals) {
            goalsRepository.insert(goal);
        }
    }


    public Boolean getLoading() {
        return loading;
    }
    public void setLoading(Boolean loading) {
        this.loading = loading;
    }
}
