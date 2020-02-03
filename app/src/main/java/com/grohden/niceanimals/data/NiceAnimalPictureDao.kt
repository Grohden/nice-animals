package com.grohden.niceanimals.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grohden.niceanimals.shibe.service.AnimalType

@Dao
interface NiceAnimalPictureDao {
  @Query("SELECT * FROM nice_animal_pictures")
  fun getAll(): LiveData<List<NiceAnimalPicture>>

  @Query("SELECT * FROM nice_animal_pictures WHERE type = :animalType")
  fun getAllByType(animalType: AnimalType): LiveData<List<NiceAnimalPicture>>

  @Query("SELECT * FROM nice_animal_pictures WHERE id = :animalId")
  fun getSingleById(animalId: Long): LiveData<NiceAnimalPicture>

  @Insert
  fun insertSingle(picture: NiceAnimalPicture): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(animals: List<NiceAnimalPicture>)

  @Delete
  fun deleteSingle(picture: NiceAnimalPicture)
}
