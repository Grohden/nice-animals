package com.grohden.niceanimals.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.adapters.GalleryAdapter
import com.grohden.niceanimals.ui.base.BaseActivity
import com.grohden.niceanimals.ui.extensions.getEnumExtra
import com.grohden.niceanimals.ui.extensions.isEnum
import com.grohden.niceanimals.ui.extensions.putEnumExtra
import io.realm.Realm
import kotlinx.android.synthetic.main.gallery.*

class GalleryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: implement other life cycle methods and just resume the activity instead
        // of destroying it
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery)

        val imagePosition = intent.getIntExtra(IMAGE_URL_EXTRA, 0)
        val animalType = intent.getEnumExtra(ANIMAL_TYPE_EXTRA) as AnimalType
        configureRV(imagePosition, animalType)
    }

    private fun configureRV(position: Int, animalType: AnimalType) {
        val niceAnimals = Realm.getDefaultInstance()
                .where<NiceAnimal>(NiceAnimal::class.java)
                .isEnum("type", animalType)
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

    companion object {
        private const val IMAGE_URL_EXTRA = "IMAGE_URL_EXTRA"
        private const val ANIMAL_TYPE_EXTRA = "ANIMAL_TYPE_EXTRA"

        fun createIntent(context: Context, imagePosition: Int, animalType: AnimalType): Intent {
            return Intent(
                    context,
                    GalleryActivity::class.java
            ).apply {
                putExtra(IMAGE_URL_EXTRA, imagePosition)
                putEnumExtra(ANIMAL_TYPE_EXTRA, animalType)
            }
        }
    }
}
