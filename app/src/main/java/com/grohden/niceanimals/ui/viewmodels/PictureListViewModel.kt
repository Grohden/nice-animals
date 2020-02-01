package com.grohden.niceanimals.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grohden.niceanimals.data.PictureRepository
import com.grohden.niceanimals.shibe.service.AnimalType
import kotlinx.coroutines.launch

class PictureListViewModel internal constructor(
  private val picturesRepository: PictureRepository,
  private val animalType: AnimalType
) : ViewModel() {
  val pictures = picturesRepository.getAnimalTypePictures(animalType)

  fun fetchMore() {
    viewModelScope.launch {
      picturesRepository.fetchMore(animalType)
    }
  }
}