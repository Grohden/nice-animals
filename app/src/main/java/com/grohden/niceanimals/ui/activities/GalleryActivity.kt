package com.grohden.niceanimals.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.ui.adapters.GalleryAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.gallery.*

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: implement other life cycle methods and just resume the activity instead
        // of destroying it
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery)

        val imagePosition = intent.getIntExtra(IMAGE_URL_EXTRA, 0)
        configureRV(imagePosition)
    }

    private fun configureRV(position: Int) {
        val niceAnimals = Realm.getDefaultInstance()
                .where<NiceAnimal>(NiceAnimal::class.java)
                .findAll()

        val manager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        )
        val galleryAdapter = GalleryAdapter(niceAnimals)

        PagerSnapHelper()
                .attachToRecyclerView(galleryRV)

        galleryRV.layoutManager = manager
        galleryRV.adapter = galleryAdapter
        galleryRV.scrollToPosition(position)
    }

    companion object {
        val IMAGE_URL_EXTRA = "IMAGE_URL_EXTRA"
    }
}
