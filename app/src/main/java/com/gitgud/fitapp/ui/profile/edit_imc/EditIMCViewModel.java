package com.gitgud.fitapp.ui.profile.edit_imc;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gitgud.fitapp.data.model.HistoryStat;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.UserDataSource;
import com.gitgud.fitapp.entities.user.AddUserStatMutation;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditIMCViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private UserDataSource userDataSource;
    private LiveData<User> userLiveData;
    public EditIMCViewModel(@NotNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        this.userDataSource = UserDataSource.getInstance();
        userLiveData = userRepository.getCurrentUser();

    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public Observable<AddUserStatMutation.Data> saveIMC (String dbId, long localId,Double imc, Double height, Double weight){

            HistoryStat historyStat =  new HistoryStat(imc, height, weight);
            historyStat.setUserId(localId);
            userRepository.insertNewStat(historyStat);


        return userDataSource.postStat(dbId, historyStat )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
