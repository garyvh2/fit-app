package com.gitgud.fitapp.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.gitgud.fitapp.entities.user.AddUserMutation;
import com.gitgud.fitapp.entities.user.GetAllUsersQuery;
import com.gitgud.fitapp.entities.user.LoginUserQuery;
import com.gitgud.fitapp.provider.network.graphql.ApolloProvider;
import com.gitgud.fitapp.type.UserTypes;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

public class UserDataSource {

    @Nullable
    private static UserDataSource INSTANCE;

    private UserDataSource() {}

    public static UserDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (UserDataSource.class) {
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

    public Observable<LoginUserQuery.Data> loginUser (@NonNull String email, @NonNull String password) {
        /**
         * Query Build
         */
        ApolloCall<LoginUserQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(
                        LoginUserQuery.builder().email(email).password(password)
                                .build()
                );
        /**
         * API Call
         */
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    public Observable<AddUserMutation.Data> addUser (@NotNull String name, @NonNull String lastName,
                                                     @NonNull String email, @NonNull String password,
                                                     @NonNull String birthDate) {
        /**
         * Query Build
         */
        ApolloCall<AddUserMutation.Data> apolloCall = ApolloProvider.getApolloInstance()
                .mutate(
                        AddUserMutation.builder().name(name).lastName(lastName).email(email)
                                .password(password).userType(UserTypes.CUSTOMER).birthDate(birthDate)
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
