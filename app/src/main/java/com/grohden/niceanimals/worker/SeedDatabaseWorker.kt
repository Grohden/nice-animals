package com.grohden.niceanimals.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.data.AppDatabase
import com.grohden.niceanimals.data.NiceAnimalPicture
import com.grohden.niceanimals.shibe.service.RemoteAnimalType
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
  context: Context,
  workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

  private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }
  private val database by lazy { AppDatabase.getInstance(applicationContext) }

  override suspend fun doWork(): Result = coroutineScope {
    try {
      val shibeService = InjectorUtil.provideShibeService()

      // TODO(gabriel.rohden): Find out how to consume in parallel these singles.
      RemoteAnimalType.values().forEach { type ->
        val animals = shibeService
          .fetchNiceImageUrls(type, 10)
          .blockingGet()
          .map { url -> NiceAnimalPicture(url, type.convertToLocal()) }

        database.animalDao().insertAll(animals)
      }

      Result.success()
    } catch (ex: Exception) {
      Log.e(TAG, "Error seeding database", ex)
      Result.failure()
    }
  }
}
