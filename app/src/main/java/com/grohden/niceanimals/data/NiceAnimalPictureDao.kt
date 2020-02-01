package com.grohden.niceanimals.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.grohden.niceanimals.shibe.service.AnimalType

@Dao
interface NiceAnimalPictureDao {
  @Query("SELECT * FROM nice_animal_pictures")
  fun getAllAnimalPictures(): LiveData<List<NiceAnimalPicture>>

  @Query("SELECT * FROM nice_animal_pictures WHERE type = :animalType")
  fun getAnimalTypePictures(animalType: AnimalType): LiveData<List<NiceAnimalPicture>>

  @Query("SELECT * FROM nice_animal_pictures WHERE id = :animalId")
  fun getAnimalPictureById(animalId: Long): LiveData<NiceAnimalPicture>

  @Insert
  fun insertAnimalPicture(picture: NiceAnimalPicture): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(animals: List<NiceAnimalPicture>)

  @Delete
  fun deleteAnimalPicture(picture: NiceAnimalPicture)
}