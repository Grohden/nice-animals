package com.grohden.niceanimals.base.di.koin

import com.google.gson.GsonBuilder
import com.grohden.niceanimals.base.di.koin.WebserviceUrls.SHIBE_SERVICE
import com.grohden.niceanimals.services.NiceAnimalsService
import com.grohden.niceanimals.shibe.service.ShibeService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// just declare it
val netModule = module {
    //Gson
    single {
        GsonBuilder().create()
    }
    single {
        createOkHttpClient()
    }
    single {
        NiceAnimalsService(get())
    }
    single {
        createWebService<ShibeService>(get(), SHIBE_SERVICE)
    }
}

object WebserviceUrls {
    const val SHIBE_SERVICE = "https://shibe.online/api/"
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(T::class.java)
}