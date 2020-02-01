package com.grohden.niceanimals.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.grohden.niceanimals.shibe.service.AnimalType

@Entity(
  tableName = "nice_animal_pictures",
  indices = [Index("url")]
)
data class NiceAnimalPicture(
  @ColumnInfo(name = "url") val url: String,
  @ColumnInfo(name = "type") val type: AnimalType
) {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  var id: Long = 0
}