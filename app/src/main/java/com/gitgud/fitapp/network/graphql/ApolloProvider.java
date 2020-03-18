package com.gitgud.fitapp.network.graphql;

import com.apollographql.apollo.ApolloClient;
import com.gitgud.fitapp.BuildConfig;

public class ApolloProvider {

    private static ApolloClient INSTANCE = null;

    public static ApolloClient getApolloInstance() {
        if (INSTANCE == null) {
            synchronized (ApolloClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = ApolloClient.builder()
                            .serverUrl(BuildConfig.REST_URL)
                            .okHttpClient(OkHttpProvider.provideOkHttpClient())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroy() {
        INSTANCE = null;
    }
}