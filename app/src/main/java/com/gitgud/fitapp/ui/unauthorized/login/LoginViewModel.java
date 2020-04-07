package com.gitgud.fitapp.ui.unauthorized.login;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gitgud.fitapp.BR;
import com.gitgud.fitapp.data.source.UserDataSource;
import com.gitgud.fitapp.entities.user.AddUserMutation;
import com.gitgud.fitapp.entities.user.LoginUserQuery;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseObservable {
    // INJECTION START
    @NonNull
    private UserDataSource userDataSource;
    // ATTRIBUTES START
    private Boolean loading;


    public LoginViewModel(@NonNull UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public Observable<LoginUserQuery.Data> loginObservable(@NotNull String email, @NonNull String password) {
        setLoading(true);
        return this.userDataSource
                .loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Bindable
    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(com.gitgud.fitapp.BR.viewModel);
    }
}
