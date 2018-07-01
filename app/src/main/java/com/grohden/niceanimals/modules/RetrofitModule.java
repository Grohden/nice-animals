package com.grohden.niceanimals.modules;

import com.grohden.niceanimals.shibe.service.ShibeService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//TODO: change to a dagger module and inject instead.
public class RetrofitModule {

    private static Retrofit retrofitConfig() {
        return new Retrofit
                .Builder()
                .baseUrl("http://shibe.online/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ShibeService getShibeService() {
        return retrofitConfig().create(ShibeService.class);
    }
}
