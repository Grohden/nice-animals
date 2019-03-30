package com.grohden.niceanimals.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.realm.entities.NiceAnimalFields
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.adapters.GalleryAdapter
import com.grohden.niceanimals.ui.base.BaseFragment
import com.grohden.niceanimals.ui.extensions.isEnum
import io.realm.Realm
import kotlinx.android.synthetic.main.gallery.*

open class GalleryFragment : BaseFragment() {

    private var imagePosition: Int = 0
    lateinit var animalType: AnimalType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = GalleryFragmentArgs.fromBundle(arguments!!)
        imagePosition = bundle.imagePosition
        animalType = bundle.animalType
    }

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRV(imagePosition, animalType)
    }

    private fun configureRV(position: Int, animalType: AnimalType) {
        val niceAnimals = Realm.getDefaultInstance()
            .where(NiceAnimal::class.java)
            .isEnum(NiceAnimalFields.TYPE, animalType)
            .findAll()

        val manager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val galleryAdapter = GalleryAdapter(niceAnimals)

        PagerSnapHelper()
            .attachToRecyclerView(galleryRV)

        galleryRV.apply {
            layoutManager = manager
            adapter = galleryAdapter
            scrollToPosition(position)
        }
    }
}
