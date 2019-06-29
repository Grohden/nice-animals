package com.grohden.niceanimals.base.di.manual

import android.content.Context
import com.grohden.niceanimals.base.di.koin.createWebService
import com.grohden.niceanimals.data.AppDatabase
import com.grohden.niceanimals.data.PictureRepository
import com.grohden.niceanimals.shibe.service.ShibeService
import com.grohden.niceanimals.ui.viewmodels.PictureListViewFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
  return Retrofit.Builder()
    .baseUrl(url)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(T::class.java)
}

object InjectorUtil {

  private fun getPicturesRepository(context: Context): PictureRepository {
    return PictureRepository.getInstance(
      AppDatabase.getInstance(context.applicationContext).animalDao())
  }

  private fun getOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    return OkHttpClient.Builder()
      .connectTimeout(30L, TimeUnit.SECONDS)
      .readTimeout(30L, TimeUnit.SECONDS)
      .addInterceptor(httpLoggingInterceptor)
      .build()
  }

  fun providePicturesListViewModelFactory(
    context: Context
  ): PictureListViewFactory {
    val repository = getPicturesRepository(context)
    return PictureListViewFactory(repository)
  }


  fun provideShibeService(): ShibeService {
    return createWebService(
      getOkHttpClient(),
      "https://shibe.online/api/"
    )
  }
}