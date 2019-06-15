package com.grohden.niceanimals.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.data.NiceAnimalPicture
import com.grohden.niceanimals.data.PictureRepository
import com.grohden.niceanimals.shibe.service.AnimalType
import kotlinx.coroutines.launch

class PictureListViewModel internal constructor(
  private val picturesRepository: PictureRepository
) : ViewModel() {
  val pictures = picturesRepository.getAnimalPictures()

  fun fetchMore() {
    viewModelScope.launch {
      picturesRepository.fetchMore(AnimalType.shibes)
    }
  }
}