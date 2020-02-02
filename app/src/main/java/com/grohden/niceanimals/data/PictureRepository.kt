package com.grohden.niceanimals.data

import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.shibe.service.AnimalType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PictureRepository private constructor(
  private val niceAnimalPictureDao: NiceAnimalPictureDao
) {

  suspend fun createAnimalPicture(url: String, type: AnimalType) {
    withContext(IO) {
      niceAnimalPictureDao.insertSingle(
        picture = NiceAnimalPicture(url, type)
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
      niceAnimalPictureDao.deleteSingle(picture)
    }
  }

  suspend fun fetchMore(type: AnimalType) = withContext(IO) {
    val shibeService = InjectorUtil.provideShibeService()

    val newBoys = shibeService
      .fetchNiceImageUrls(type.convertToRemote(), 10)
      .blockingGet()
      .map { url -> NiceAnimalPicture(url, type) }

    niceAnimalPictureDao.insertAll(newBoys)
  }

  fun getAllAnimalPictures() =
    niceAnimalPictureDao.getAll()

  fun getAnimalTypePictures(type: AnimalType) =
    niceAnimalPictureDao.getAllByType(type)

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