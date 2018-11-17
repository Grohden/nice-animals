package com.grohden.niceanimals.ui.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import blade.Blade
import blade.Extra
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.realm.entities.NiceAnimalFields
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.adapters.GalleryAdapter
import com.grohden.niceanimals.ui.base.BaseActivity
import com.grohden.niceanimals.ui.extensions.isEnum
import io.realm.Realm
import kotlinx.android.synthetic.main.gallery.*

@Blade
open class GalleryActivity : BaseActivity() {

    @Extra
    @JvmField
    var imagePosition: Int = 0

    @Extra
    lateinit var animalType: AnimalType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery)

        configureRV(imagePosition, animalType)
    }

    private fun configureRV(position: Int, animalType: AnimalType) {
        val niceAnimals = Realm.getDefaultInstance()
            .where<NiceAnimal>(NiceAnimal::class.java)
            .isEnum(NiceAnimalFields.TYPE, animalType)
            .findAll()

        val manager = LinearLayoutManager(
            this,
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
