package com.gitgud.fitapp.ui.unauthorized.registration;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.gitgud.fitapp.BR;
import com.gitgud.fitapp.data.source.UserDataSource;
import com.gitgud.fitapp.entities.user.AddUserMutation;

import org.jetbrains.annotations.NotNull;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class RegistrationViewModel extends BaseObservable {

    // INJECTION START
    @NonNull
    private UserDataSource userDataSource;
    // ATTRIBUTES START
    private Boolean loading;

    public RegistrationViewModel(@NonNull UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public Observable<AddUserMutation.Data> addUserObservable(@NotNull String name, @NonNull String lastName,
                                                              @NonNull String email, @NonNull String password,
                                                              @NonNull String birthDate) {
        setLoading(true);
        return this.userDataSource
                .addUser(name, lastName, email, password, birthDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Bindable
    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.viewModel);
    }
}
