package com.grohden.niceanimals.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grohden.niceanimals.data.PictureRepository
import com.grohden.niceanimals.shibe.service.AnimalType

class PictureListViewFactory(
  private val repository: PictureRepository,
  private val animalType: AnimalType
) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return PictureListViewModel(repository, animalType) as T
  }
}