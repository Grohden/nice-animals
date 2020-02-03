package com.grohden.niceanimals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grohden.niceanimals.R
import com.grohden.niceanimals.data.NiceAnimalPicture
import com.grohden.niceanimals.databinding.ListItemViewBinding
import com.grohden.niceanimals.ui.adapters.diff.PictureDiffCallback
import com.grohden.niceanimals.ui.viewmodels.PictureViewModel

class PictureViewAdapter : ListAdapter<NiceAnimalPicture, PictureViewAdapter.ViewHolder>(
  PictureDiffCallback()
) {

  init {
    setHasStableIds(true)
  }

  override fun getItemId(position: Int): Long {
    return getItem(position).id
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    ListItemViewBinding.inflate(
      LayoutInflater.from(parent.context)
    )
    return ViewHolder(
      DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.list_item_view, parent, false
      )
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    getItem(position).let { picture ->
      with(holder) {
        itemView.tag = picture.url
        itemView.transitionName = picture.url
        bind(picture)
      }
    }
  }

  class ViewHolder(
    private val binding: ListItemViewBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(boundPicture: NiceAnimalPicture) {
      with(binding) {
        picture = PictureViewModel(picture = boundPicture)
        executePendingBindings()
      }
    }
  }
}
