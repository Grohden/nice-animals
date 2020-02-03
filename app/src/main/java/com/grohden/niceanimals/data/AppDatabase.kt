package com.grohden.niceanimals.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.grohden.niceanimals.AppConstants.DATABASE_NAME
import com.grohden.niceanimals.worker.SeedDatabaseWorker

@Database(entities = [NiceAnimalPicture::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun animalDao(): NiceAnimalPictureDao

  companion object {

    // For Singleton instantiation
    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      return instance ?: synchronized(this) {
        instance ?: buildDatabase(context).also { instance = it }
      }
    }

    // Create and pre-populate the database. See this article for more details:
    // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
    private fun buildDatabase(context: Context): AppDatabase {
      return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .addCallback(object : RoomDatabase.Callback() {
          override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
            WorkManager.getInstance(context).enqueue(request)
          }
        })
        .build()
    }
  }
}
