package com.grohden.niceanimals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.ui.holders.GalleryViewHolder
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class GalleryAdapter(results: RealmResults<NiceAnimal>) :
  RealmRecyclerViewAdapter<NiceAnimal, GalleryViewHolder>(results, true) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val v = inflater.inflate(
      R.layout.gallery_image,
      parent,
      false
    )

    return GalleryViewHolder(v)
  }

  override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
    val animalURL = getItem(position)
    holder.bindAnimal(animalURL!!)
  }
}
