package com.grohden.niceanimals.ui.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.grohden.niceanimals.data.NiceAnimalPicture

class PictureViewModel(picture: NiceAnimalPicture) : ViewModel() {
  val url = ObservableField(picture.url)
  val type = picture.type
}