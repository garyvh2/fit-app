package com.gitgud.fitapp.ui.rewards.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.data.respository.UserRepository;
import com.gitgud.fitapp.data.source.RewardDataSource;
import com.gitgud.fitapp.entities.reward.GetRewardsByUserQuery;
import com.gitgud.fitapp.entities.reward.GetRewardsQuery;
import com.gitgud.fitapp.entities.reward.InsertUserRewardMutation;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RewardsListViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private UserRepository userRepository;
    private RewardDataSource rewardDataSource;

    public RewardsListViewModel(@NonNull Application application) {
        super(application);
        rewardDataSource = RewardDataSource.getInstance();
        userRepository = new UserRepository(application);
    }

    public User getCurrentUser() {
        return userRepository.getCurrentUserSync();
    }

    public Observable<Optional<InsertUserRewardMutation.AddUserReward>> insertUserReward(String email, String rewardId) {
        return this.rewardDataSource.insertUserReward(email, rewardId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
