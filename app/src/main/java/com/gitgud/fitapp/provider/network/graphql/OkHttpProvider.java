package com.gitgud.fitapp.provider.network.graphql;

import com.gitgud.fitapp.provider.network.interceptors.ContentTypeInterceptor;

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