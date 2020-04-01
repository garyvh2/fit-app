package com.gitgud.fitapp.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.gitgud.fitapp.entities.user.GetAllUsersQuery;
import com.gitgud.fitapp.provider.network.graphql.ApolloProvider;

import io.reactivex.Observable;

public class UserDataSource {

    @Nullable
    private static UserDataSource INSTANCE;

    private UserDataSource() {}

    public static UserDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (PokemonDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public Observable<GetAllUsersQuery.Data> getUsers (@NonNull String id) {
        /**
         * Query Build
         */
        ApolloCall<GetAllUsersQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        GetAllUsersQuery.builder()
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
