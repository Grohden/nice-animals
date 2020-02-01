package com.grohden.niceanimals.shibe.service

enum class AnimalType {
  // Today we learned that shibes aren't dogs, they are their own type.
  Shibe,
  Cat,
  Bird;

  fun convertToRemote(): RemoteAnimalType {
    return when (this) {
      Shibe -> RemoteAnimalType.shibes
      Cat -> RemoteAnimalType.cats
      Bird -> RemoteAnimalType.birds
    }
  }
}

// This class is for shibe online api, do not change these entries.
@Suppress("EnumEntryName")
enum class RemoteAnimalType {
  shibes,
  cats,
  birds;

  fun convertToLocal(): AnimalType {
    return when (this) {
      shibes -> AnimalType.Shibe
      cats -> AnimalType.Cat
      birds -> AnimalType.Bird
    }
  }
}