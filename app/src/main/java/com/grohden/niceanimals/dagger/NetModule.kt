package com.grohden.niceanimals.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.grohden.niceanimals.shibe.service.ShibeService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.java8.Java8CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(
            @Named("baseUrl") baseUrl: String,
            gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideShibeService(retrofit: Retrofit): ShibeService {
        return retrofit.create(ShibeService::class.java)
    }
}
