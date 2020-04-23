package com.gitgud.fitapp.ui.rewards.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.RewardDataSource;
import com.gitgud.fitapp.entities.reward.GetRewardsByUserQuery;
import com.gitgud.fitapp.entities.reward.GetRewardsQuery;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RewardsListUserViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private UserRepository userRepository;
    private RewardDataSource rewardDataSource;

    public RewardsListUserViewModel(@NonNull Application application) {
        super(application);
        rewardDataSource = RewardDataSource.getInstance();
        userRepository = new UserRepository(application);
    }

    public User getCurrentUser() {
        return userRepository.getCurrentUserSync();
    }

    public Observable<Optional<List<GetRewardsQuery.GetReward>>> getRewards() {
        return this.rewardDataSource.getRewards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Optional<List<GetRewardsByUserQuery.UserReward>>> getRewardsByUser(String email) {
        return this.rewardDataSource.getRewardsByUser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
