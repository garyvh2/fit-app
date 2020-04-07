package com.gitgud.fitapp.ui.unauthorized.splash;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.UserRepository;

import org.jetbrains.annotations.NotNull;

public class SplashViewModel  extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<User> loggedUser;

    public SplashViewModel(@NotNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        loggedUser = userRepository.getCurrentUser();
    }

    public LiveData<User> getLoggedUser() {
        return loggedUser;
    }

}