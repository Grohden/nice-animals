package com.grohden.niceanimals.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grohden.niceanimals.data.PictureRepository

class PictureListViewFactory(
  private val repository: PictureRepository
) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return PictureListViewModel(repository) as T
  }
}