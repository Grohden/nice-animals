package com.grohden.niceanimals.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import blade.Arg
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.realm.entities.NiceAnimalFields
import com.grohden.niceanimals.services.NiceAnimalsService
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.adapters.NiceCollectionAdapter
import com.grohden.niceanimals.ui.base.BaseFragment
import com.grohden.niceanimals.ui.extensions.isEnum
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import kotlinx.android.synthetic.main.animal_grid_fragment.*
import org.koin.android.ext.android.inject

class AnimalGridFragment : BaseFragment() {

    companion object {
        private const val COLUMN_SPAN = 2
    }

    private val disposables = CompositeDisposable()

    private val niceAnimalsService: NiceAnimalsService by inject()

    private var isLoadingMore = false

    @Arg
    lateinit var animalType: AnimalType

    private val primaryDark by lazy {
        ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.animal_grid_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNiceAnimalsRV()
        configureRefresher()
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    private fun configureRefresher() {
        swipeRefresher.setColorSchemeColors(primaryDark)
        swipeRefresher.setOnRefreshListener {
            // FIXME: what happens when you try to refresh while loading more?
            // FIXME: crash, that`s what happens. (realm query vs realm delete breaks)
            niceAnimalsService
                .refreshAnimalType(animalType)
                .subscribe { _, _ -> swipeRefresher.isRefreshing = false }
        }
    }

    private fun createCollectionAdapter(): NiceCollectionAdapter {
        val niceAnimals = Realm.getDefaultInstance()
            .where(NiceAnimal::class.java)
            .isEnum(NiceAnimalFields.TYPE, animalType)
            .findAll()

        val niceAdapter = NiceCollectionAdapter(niceAnimals, animalType)

        niceAdapter
            .onReachBottom()
            .filter { !isLoadingMore }
            .subscribe { loadMoreNiceImages() }
            .also { disposables.add(it) }

        niceAdapter
            .observeClicks()
            .subscribe { position ->
                openAnimalPicture(position)
            }.also { disposables.add(it) }

        return niceAdapter
    }

    private fun openAnimalPicture(position: Int) {
        val directions = ContentTabsFragmentDirections.openAnimalPicture(
            position,
            animalType
        )

        NavHostFragment
            .findNavController(this@AnimalGridFragment)
            .navigate(directions)
    }

    private fun configureNiceAnimalsRV() {
        val rvLayoutManager = GridLayoutManager(requireContext(), COLUMN_SPAN)

        animalListRV.apply {
            setHasFixedSize(true)
            layoutManager = rvLayoutManager
            adapter = createCollectionAdapter()
        }
    }

    private fun loadMoreNiceImages() {

        if (swipeRefresher.isRefreshing) {
            return
        }

        isLoadingMore = true
        niceAnimalsService
            .fetchAndPersistMore(animalType)
            .subscribe { _, _ ->
                isLoadingMore = false
            }.also { disposables.add(it) }
    }
}