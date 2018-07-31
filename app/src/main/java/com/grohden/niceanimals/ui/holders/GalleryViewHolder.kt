package com.grohden.niceanimals.ui.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Callback {

    private val progressBar: ProgressBar by lazy {
        itemView.findViewById<ProgressBar>(R.id.gallery_image_loader)
    }

    private val animalImage: ImageView by lazy {
        itemView.findViewById<ImageView>(R.id.gallery_image)
    }

    fun bindAnimal(animal: NiceAnimal) {
        Picasso.get()
                .load(animal.pictureUrl)
                .fit()
                .centerInside()
                .into(animalImage, this)
    }

    override fun onSuccess() {
        progressBar.visibility = View.GONE
    }

    override fun onError(e: Exception) {
        progressBar.visibility = View.GONE
    }
}
