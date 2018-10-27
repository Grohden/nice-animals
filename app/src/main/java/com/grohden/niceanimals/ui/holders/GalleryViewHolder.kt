package com.grohden.niceanimals.ui.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grohden.niceanimals.extensions.at
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gallery_image.view.*

class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindAnimal(animal: NiceAnimal) {
        Picasso.get()
            .load(animal.pictureUrl)
            .fit()
            .centerInside()
            .at(itemView.galleryImage) {
                itemView.galleryImageLoader.visibility = View.GONE
            }
    }
}
