package com.grohden.niceanimals.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NiceAnimalPictureDao {
  @Query("SELECT * FROM nice_animal_pictures")
  fun getAnimalPictures(): LiveData<List<NiceAnimalPicture>>

  @Query("SELECT * FROM nice_animal_pictures WHERE id = :animalId")
  fun getAnimalPicture(animalId: Long): LiveData<NiceAnimalPicture>

  @Insert
  fun insertAnimalPicture(picture: NiceAnimalPicture): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(animals: List<NiceAnimalPicture>)

  @Delete
  fun deleteAnimalPicture(picture: NiceAnimalPicture)
}