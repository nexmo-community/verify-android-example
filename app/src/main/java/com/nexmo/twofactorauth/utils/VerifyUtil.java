package com.nexmo.twofactorauth.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class VerifyUtil {

    private final Retrofit retrofit;
    private VerifyService verifyService;
    private static VerifyUtil instance = null;

    private VerifyUtil() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://nexmo-verify.glitch.me")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        verifyService = retrofit.create(VerifyService.class);
    }

    public static VerifyUtil getInstance() {
        if (instance == null) {
            instance = new VerifyUtil();
        }
        return instance;
    }

    public VerifyService getVerifyService() {
        return verifyService;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
