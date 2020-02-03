package com.grohden.niceanimals.ui.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.grohden.niceanimals.data.NiceAnimalPicture

class PictureDiffCallback : DiffUtil.ItemCallback<NiceAnimalPicture>() {

  override fun areItemsTheSame(
    oldItem: NiceAnimalPicture,
    newItem: NiceAnimalPicture
  ): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(
    oldItem: NiceAnimalPicture,
    newItem: NiceAnimalPicture
  ): Boolean {
    return oldItem.url == newItem.url
  }
}
