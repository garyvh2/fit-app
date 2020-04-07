package com.gitgud.fitapp.ui.unauthorized.login;

import android.app.Application;

import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;

import com.gitgud.fitapp.data.source.UserDataSource;
import com.gitgud.fitapp.entities.user.LoginUserQuery;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    // INJECTION START
    @NonNull
    private UserDataSource userDataSource;
    // ATTRIBUTES START
    private Boolean loading;

    public LoginViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        this.userDataSource = UserDataSource.getInstance();
    }

    public Observable<LoginUserQuery.Data> loginObservable(@NotNull String email, @NonNull String password) {
        setLoading(true);
        return this.userDataSource
                .loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Boolean getLoading() {
        return loading;
    }
    public void setLoading(Boolean loading) {
        this.loading = loading;
    }
}
