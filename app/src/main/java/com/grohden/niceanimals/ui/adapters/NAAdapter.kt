package com.grohden.niceanimals.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.ui.holders.NAViewHolder
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class NAAdapter(results: RealmResults<NiceAnimal>) : RealmRecyclerViewAdapter<NiceAnimal, NAViewHolder>(results, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NAViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(
                R.layout.animal_card,
                parent,
                false
        )

        return NAViewHolder(v)
    }

    override fun onBindViewHolder(niceHolder: NAViewHolder, position: Int) {
        val animalURL = getItem(position)
        niceHolder.bindAnimal(animalURL!!)
    }
}
