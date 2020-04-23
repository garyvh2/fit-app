package com.gitgud.fitapp.data.source;

import androidx.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.gitgud.fitapp.data.model.Goal;
import com.gitgud.fitapp.entities.user.AddUserGoalMutation;
import com.gitgud.fitapp.entities.user.UpdateUserGoalMutation;
import com.gitgud.fitapp.provider.network.graphql.ApolloProvider;
import com.gitgud.fitapp.type.HistoryStatsInputType;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

public class GoalDataSource {

    @Nullable
    private static GoalDataSource INSTANCE;

    private GoalDataSource() {}

    public static GoalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (UserDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GoalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public Observable<AddUserGoalMutation.Data> postGoal(@NotNull String id, Goal goal) {

        ApolloCall<AddUserGoalMutation.Data> apolloCall = ApolloProvider.getApolloInstance()
                .mutate(
                        AddUserGoalMutation.builder().userId(id)
                                .name(goal.getName())
                                .current(goal.getProgress())
                                .objective(goal.getGoal())
                                .limitDate(goal.getDate())
                                .type(goal.getGoalType())
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    public Observable<UpdateUserGoalMutation.Data> updateGoal(@NotNull String id, Goal goal) {
        ApolloCall<UpdateUserGoalMutation.Data> apolloCall = ApolloProvider.getApolloInstance()
                .mutate(
                        UpdateUserGoalMutation.builder().userId(id)
                                .goalId(goal.getDbId())
                                .name(goal.getName())
                                .current(goal.getProgress())
                                .objective(goal.getGoal())
                                .limitDate(goal.getDate())
                                .type(goal.getGoalType())
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

}
