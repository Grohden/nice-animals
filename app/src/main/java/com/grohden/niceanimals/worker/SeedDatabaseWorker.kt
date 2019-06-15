package com.grohden.niceanimals.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.data.AppDatabase
import com.grohden.niceanimals.data.NiceAnimalPicture
import com.grohden.niceanimals.shibe.service.AnimalType
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
  context: Context,
  workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

  private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

  override suspend fun doWork(): Result = coroutineScope {

    try {
      val shibeService = InjectorUtil.provideShibeService()


      val doggos = shibeService
        .fetchNiceImageUrls(AnimalType.shibes, 10)
        .blockingGet()
        .map(::NiceAnimalPicture)

      val database = AppDatabase.getInstance(applicationContext)
      database.animalDao().insertAll(doggos)

      Result.success()
    } catch (ex: Exception) {
      Log.e(TAG, "Error seeding database", ex)
      Result.failure()
    }
  }
}