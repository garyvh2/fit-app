package com.gitgud.fitapp.provider.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ContentTypeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.method(originalRequest.method(), originalRequest.body());
        return chain.proceed(builder.build());
    }
}