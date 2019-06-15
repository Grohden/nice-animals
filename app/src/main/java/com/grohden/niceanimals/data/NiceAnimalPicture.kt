package com.grohden.niceanimals.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "nice_animal_pictures",
  indices = [Index("url")]
)
data class NiceAnimalPicture(
  @ColumnInfo(name = "url") val url: String
) {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  var id: Long = 0
}