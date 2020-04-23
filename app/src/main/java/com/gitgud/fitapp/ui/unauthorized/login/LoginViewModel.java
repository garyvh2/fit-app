package com.gitgud.fitapp.ui.unauthorized.login;

import android.app.Application;

import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;

import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.data.model.HistoryStat;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.GoalsRepository;
import com.gitgud.fitapp.data.respository.RoutineRepository;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.UserDataSource;
import com.gitgud.fitapp.entities.user.LoginUserQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    private RoutineRepository routineRepository;
    // ATTRIBUTES START
    private Boolean loading;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        goalsRepository =  new GoalsRepository(application);
        routineRepository =  new RoutineRepository(application);
        this.userDataSource = UserDataSource.getInstance();
    }


    public Observable<LoginUserQuery.Data> loginObservable(@NotNull String email, @NonNull String password) {
        setLoading(true);
        return this.userDataSource
                .loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void saveLoggedUser(User user, List<HistoryStat> historyStatList) {
        userRepository.insert(user, historyStatList);
    }

    public  void saveGoals(List<Goal> goals) {
       goalsRepository.insertAll(goals);
    }

    public void createRoutine () {
        routineRepository.initBaseRoutine();
    }


    public Boolean getLoading() {
        return loading;
    }
    public void setLoading(Boolean loading) {
        this.loading = loading;
    }
}
