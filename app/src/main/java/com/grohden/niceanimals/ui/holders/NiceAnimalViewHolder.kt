package com.grohden.niceanimals.ui.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grohden.niceanimals.R
import com.grohden.niceanimals.extensions.at
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.activities.GalleryActivity
import com.jakewharton.rxbinding2.view.clicks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.animal_card.view.*

class NiceAnimalViewHolder(private val animalCard: View, private val type: AnimalType) :
    RecyclerView.ViewHolder(animalCard) {

    init {
        animalCard
            .picImageView
            .clicks()
            .subscribe { openFullscreenImageActivity() }
    }

    fun bindAnimal(animal: NiceAnimal) {
        animalCard.picProgressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(animal.pictureUrl)
            .resizeDimen(R.dimen.min_image_width, R.dimen.min_image_height)
            .centerCrop()
            .at(animalCard.picImageView) {
                animalCard.picProgressBar.visibility = View.GONE
            }
    }

    private fun openFullscreenImageActivity() {
        val context = animalCard.picImageView.context!!
        val intent = GalleryActivity.createIntent(
            context,
            adapterPosition,
            type
        )

        context.startActivity(intent)
    }
}
