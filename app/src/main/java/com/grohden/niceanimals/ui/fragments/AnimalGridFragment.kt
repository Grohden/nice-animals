package com.grohden.niceanimals.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.services.NiceAnimalsService
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.adapters.NiceCollectionAdapter
import com.grohden.niceanimals.ui.base.BaseFragment
import com.grohden.niceanimals.ui.extensions.getEnum
import com.grohden.niceanimals.ui.extensions.isEnum
import com.grohden.niceanimals.ui.extensions.putEnum
import dagger.android.support.AndroidSupportInjection
import io.realm.Realm
import kotlinx.android.synthetic.main.animal_grid_fragment.*
import javax.inject.Inject


class AnimalGridFragment : BaseFragment() {

    companion object {
        private const val COLUMN_SPAN = 2
        private const val ANIMAL_TYPE = "ANIMAL_TYPE"

        fun newInstance(animalType: AnimalType): AnimalGridFragment {
            val args = Bundle()
            args.putEnum(ANIMAL_TYPE, animalType)

            val fragment = AnimalGridFragment()
            fragment.arguments = args
            return fragment
        }

    }

    @Inject
    lateinit var niceAnimalsService: NiceAnimalsService

    private var isLoadingMore = false

    private lateinit var animalType: AnimalType

    private val primaryDark by lazy {
        ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
    }

    override fun onAttach(activity: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)

        animalType = arguments!!.getEnum(ANIMAL_TYPE) as AnimalType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.animal_grid_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNiceAnimalsRV()
        configureRefresher()
    }

    private fun configureRefresher() {
        swipeRefresher.setColorSchemeColors(primaryDark)
        swipeRefresher.setOnRefreshListener {
            //FIXME: what happens when you try to refresh while loading more?
            //FIXME: crash, that`s what happens. (realm query vs realm delete breaks)
            niceAnimalsService
                    .refreshAnimalType(animalType)
                    .subscribe { _, _ -> swipeRefresher.isRefreshing = false }
        }
    }

    private fun createCollectionAdapter(): NiceCollectionAdapter {
        val niceAnimals = Realm.getDefaultInstance()
                .where<NiceAnimal>(NiceAnimal::class.java)
                .isEnum("type", animalType)
                .findAll()

        val niceAdapter = NiceCollectionAdapter(niceAnimals, animalType)

        niceAdapter
                .onReachBottom()
                .filter { !isLoadingMore }
                .subscribe { loadMoreNiceImages() }

        return niceAdapter
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
        isLoadingMore = true
        niceAnimalsService
                .fetchAndPersistMore(animalType)
                .subscribe { _, _ ->
                    isLoadingMore = false
                }
    }
}