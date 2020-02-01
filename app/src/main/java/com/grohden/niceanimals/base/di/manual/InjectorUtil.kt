package com.grohden.niceanimals.base.di.manual

import android.content.Context
import com.grohden.niceanimals.base.di.koin.createWebService
import com.grohden.niceanimals.data.AppDatabase
import com.grohden.niceanimals.data.PictureRepository
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.shibe.service.ShibeService
import com.grohden.niceanimals.ui.viewmodels.PictureListViewFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object InjectorUtil {

  private fun getPicturesRepository(context: Context): PictureRepository {
    return PictureRepository.getInstance(
      AppDatabase.getInstance(context.applicationContext).animalDao())
  }

  private fun getOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
      .connectTimeout(30L, TimeUnit.SECONDS)
      .readTimeout(30L, TimeUnit.SECONDS)
      .addInterceptor(httpLoggingInterceptor)
      .build()
  }

  fun providePicturesListViewModelFactory(
    context: Context,
    animalType: AnimalType
  ): PictureListViewFactory {
    val repository = getPicturesRepository(context)
    return PictureListViewFactory(repository, animalType)
  }

  fun provideShibeService(): ShibeService {
    return createWebService(
      getOkHttpClient(),
      "https://shibe.online/api/"
    )
  }
}