package com.grohden.niceanimals.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grohden.niceanimals.R
import com.grohden.niceanimals.data.NiceAnimalPicture
import com.grohden.niceanimals.databinding.ListItemPictureBinding
import com.grohden.niceanimals.ui.viewmodels.PictureViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PictureAdapter : ListAdapter<NiceAnimalPicture, PictureAdapter.ViewHolder>(
  PictureDiffCallback()
) {
  private val onReachBottomSubject = PublishSubject.create<Int>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    ListItemPictureBinding.inflate(
      LayoutInflater.from(parent.context)
    )
    return ViewHolder(
      DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.list_item_picture, parent, false
      )
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    getItem(position).let { picture ->
      with(holder) {
        itemView.tag = picture
        bind(createOnClickListener(picture.url), picture)
      }
    }


    if (position == itemCount - 1) {
      onReachBottomSubject.onNext(position)
    }
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    onReachBottomSubject.onComplete()
    super.onDetachedFromRecyclerView(recyclerView)
  }

  fun onReachBottom(): Observable<Int> = onReachBottomSubject


  private fun createOnClickListener(pictureUrl: String): View.OnClickListener {
    return View.OnClickListener {
      //  val direction =
      //    GardenFragmentDirections.actionGardenFragmentToPlantDetailFragment(pictureUrl)
      //  it.findNavController().navigate(direction)
    }
  }

  class ViewHolder(
    private val binding: ListItemPictureBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: View.OnClickListener, boundPicture: NiceAnimalPicture) {
      with(binding) {
        clickListener = listener
        picture = PictureViewModel(picture = boundPicture)
        //viewModel = PictureViewModel(picture)
        executePendingBindings()
      }
    }
  }
}

private class PictureDiffCallback : DiffUtil.ItemCallback<NiceAnimalPicture>() {

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