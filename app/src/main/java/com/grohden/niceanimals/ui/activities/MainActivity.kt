package com.grohden.niceanimals.ui.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.grohden.niceanimals.NiceApplication
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.services.NiceAnimalsService
import com.grohden.niceanimals.ui.adapters.NAAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var niceAnimalsService: NiceAnimalsService;

    private var isLoadingMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as NiceApplication).mNetComponent.inject(this)

        configureNiceAnimalsRV()
        configureRefresher()
    }

    private fun configureRefresher() {
        swipeRefresher.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        swipeRefresher.setOnRefreshListener {
            //FIXME: what happens when you try to refresh while loading more?
            niceAnimalsService
                    .refreshAnimals()
                    .thenRun { swipeRefresher.isRefreshing = false }
        }
    }

    private fun configureNiceAnimalsRV() {
        val rvLayoutManager = GridLayoutManager(this, 2)
        val niceAnimals = Realm.getDefaultInstance()
                .where<NiceAnimal>(NiceAnimal::class.java)
                .findAll()

        val naAdapter = NAAdapter(niceAnimals)

        animalListRV.setHasFixedSize(true)
        animalListRV.layoutManager = rvLayoutManager
        animalListRV.adapter = naAdapter
        animalListRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val isScrollDown = dy > 0
                if (isScrollDown && !isLoadingMore) {
                    val visibleItemCount = rvLayoutManager.childCount
                    val totalItemCount = rvLayoutManager.itemCount
                    val pastVisiblyItems = rvLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisiblyItems >= totalItemCount) {
                        isLoadingMore = true
                        //Need to animate the loader properly with a move animation
                        loadMoreLoader.visibility = View.VISIBLE

                        loadMoreNiceImages()
                    }
                }
            }
        })
    }

    private fun loadMoreNiceImages() {
        niceAnimalsService
                .fetchAndPersistAllTypes()
                .thenRunAsync {
                    isLoadingMore = false
                    loadMoreLoader.visibility = View.GONE
                }
    }
}
