package com.gitgud.fitapp.network.graphql;

import com.gitgud.fitapp.network.graphql.interceptors.ContentTypeInterceptor;

import okhttp3.OkHttpClient;

public class OkHttpProvider {

    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient provideOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new ContentTypeInterceptor());
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}