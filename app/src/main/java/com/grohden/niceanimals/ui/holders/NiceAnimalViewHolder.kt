package com.grohden.niceanimals.ui.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grohden.niceanimals.R
import com.grohden.niceanimals.extensions.at
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.squareup.picasso.Picasso
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.animal_card.view.*

class NiceAnimalViewHolder(private val view: View, clickSubject: PublishSubject<Int>) :
    RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener { clickSubject.onNext(adapterPosition) }
    }

    fun bindAnimal(animal: NiceAnimal) {
        view.picProgressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(animal.pictureUrl)
            .resizeDimen(R.dimen.min_image_width, R.dimen.min_image_height)
            .centerCrop()
            .at(view.picImageView) {
                view.picProgressBar.visibility = View.GONE
            }
    }
}
