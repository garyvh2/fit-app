package com.gitgud.fitapp.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.FileUpload;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.gitgud.fitapp.data.model.User;
import com.gitgud.fitapp.entities.product.GetProductBySkuQuery;
import com.gitgud.fitapp.entities.product.InsertProductMutation;
import com.gitgud.fitapp.entities.reward.GetRewardsByUserQuery;
import com.gitgud.fitapp.entities.reward.GetRewardsQuery;
import com.gitgud.fitapp.entities.reward.InsertUserRewardMutation;
import com.gitgud.fitapp.provider.network.graphql.ApolloProvider;
import com.gitgud.fitapp.type.CompanyInputType;
import com.gitgud.fitapp.type.RewardInputType;

import java.io.File;
import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;

public class RewardDataSource {
    @Nullable
    private static RewardDataSource INSTANCE;

    private RewardDataSource() {}

    public static RewardDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (RewardDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RewardDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Nullable
    public Observable<Optional<InsertUserRewardMutation.AddUserReward>> insertUserReward (String email, String rewardId) {
        ApolloCall<InsertUserRewardMutation.Data> apolloCall = ApolloProvider.getApolloInstance()
                .mutate(
                        InsertUserRewardMutation.builder()
                                .email(email)
                                .reward(RewardInputType.builder().id(rewardId).build())
                                .build()
                );

        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> {
                    InsertUserRewardMutation.Data data = dataResponse.data();
                    if (data.addUserReward() != null) {
                        return Optional.of(data.addUserReward());
                    }
                    return Optional.empty();
                });
    }

    public Observable<Optional<List<GetRewardsQuery.GetReward>>> getRewards () {
        /**
         * Query Build
         */
        ApolloCall<GetRewardsQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        GetRewardsQuery.builder()
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> {
                    GetRewardsQuery.Data data = dataResponse.data();
                    if (!data.getRewards().isEmpty()) {
                        return Optional.of(data.getRewards());
                    }
                    return Optional.empty();
                });
    }

    public Observable<Optional<List<GetRewardsByUserQuery.UserReward>>> getRewardsByUser (@NonNull String email) {
        /**
         * Query Build
         */
        ApolloCall<GetRewardsByUserQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        GetRewardsByUserQuery.builder()
                                .email(email)
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> {
                    GetRewardsByUserQuery.Data data = dataResponse.data();
                    if (!data.userRewards().isEmpty()) {
                        return Optional.of(data.userRewards());
                    }
                    return Optional.empty();
                });
    }
}
