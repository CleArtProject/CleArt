package com.coba.cleartemployee.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {
    private static final String BASE_URL = "http://cleart.space/cleart/public/";
    private static retrofitClient mInstance;
    private Retrofit retrofit;

    private retrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized retrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new retrofitClient();
        }
        return mInstance;
    }

    public API getApi() {
        return retrofit.create(API.class);
    }

}
