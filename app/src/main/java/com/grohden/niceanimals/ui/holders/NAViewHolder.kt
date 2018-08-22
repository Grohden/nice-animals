package com.grohden.niceanimals.ui.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.ui.activities.GalleryActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NAViewHolder(animalCard: View) : RecyclerView.ViewHolder(animalCard), Callback {

    private val progressBar: ProgressBar by lazy {
        animalCard.findViewById<ProgressBar>(R.id.pic_progress_bar)
    }

    private val animalView: ImageView by lazy {
        animalCard.findViewById<ImageView>(R.id.pic_image_view)
    }

    init {
        animalView.setOnClickListener(this::openFullscreenImageActivity)
    }

    fun bindAnimal(animal: NiceAnimal) {
        progressBar.visibility = View.VISIBLE

        Picasso.get()
                .load(animal.pictureUrl)
                .resizeDimen(R.dimen.min_image_width, R.dimen.min_image_height)
                .centerCrop()
                .into(animalView, this)
    }

    private fun openFullscreenImageActivity(view: View) {
        val context = view.context
        val intent = GalleryActivity.createIntent(
                context,
                adapterPosition
        )

        context.startActivity(intent)
    }


    override fun onSuccess() {
        progressBar.visibility = View.GONE
    }

    override fun onError(e: Exception) {
        progressBar.visibility = View.GONE
    }
}
