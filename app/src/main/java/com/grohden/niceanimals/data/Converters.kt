package com.grohden.niceanimals.data

import androidx.room.TypeConverter
import com.grohden.niceanimals.shibe.service.AnimalType

class Converters {
  @TypeConverter
  fun fromAnimalType(value: String?): AnimalType? {
    return value?.let { AnimalType.valueOf(value) }
  }

  @TypeConverter
  fun animalTypeToString(value: AnimalType?): String? {
    return value?.toString()
  }
}
