package com.grohden.niceanimals.data

import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.shibe.service.AnimalType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PictureRepository private constructor(
  private val niceAnimalPictureDao: NiceAnimalPictureDao
) {

  suspend fun createAnimalPicture(plantId: String) {
    withContext(IO) {
      niceAnimalPictureDao.insertAnimalPicture(
        picture = NiceAnimalPicture(plantId)
      )
    }
  }

  suspend fun createAnimalPictures(animals: List<NiceAnimalPicture>) {
    withContext(IO) {
      niceAnimalPictureDao.insertAll(animals)
    }
  }

  suspend fun removeAnimalPicture(picture: NiceAnimalPicture) {
    withContext(IO) {
      niceAnimalPictureDao.deleteAnimalPicture(picture)
    }
  }

  suspend fun fetchMore(type: AnimalType) = withContext(IO) {
    val shibeService = InjectorUtil.provideShibeService()

    val newBoys = shibeService
      .fetchNiceImageUrls(type, 10)
      .blockingGet()
      .map(::NiceAnimalPicture)

    niceAnimalPictureDao.insertAll(newBoys)
  }

  fun getAnimalPictures() =
    niceAnimalPictureDao.getAnimalPictures()

  companion object {

    // For Singleton instantiation
    @Volatile
    private var instance: PictureRepository? = null

    fun getInstance(picturesDao: NiceAnimalPictureDao) =
      instance ?: synchronized(this) {
        instance ?: PictureRepository(picturesDao).also { instance = it }
      }
  }
}