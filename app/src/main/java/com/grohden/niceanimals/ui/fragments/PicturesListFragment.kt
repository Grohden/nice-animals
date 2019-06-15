package com.grohden.niceanimals.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.databinding.FragmentPictureListBinding
import com.grohden.niceanimals.ui.adapters.PictureAdapter
import com.grohden.niceanimals.ui.viewmodels.PictureListViewModel

class PicturesListFragment : Fragment() {

  private val viewModel: PictureListViewModel by viewModels {
    InjectorUtil.providePicturesListViewModelFactory(requireContext())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = FragmentPictureListBinding.inflate(inflater, container, false)
    context ?: return binding.root

    val adapter = PictureAdapter().also {
      it
        .onReachBottom()
        .subscribe {
          viewModel.fetchMore()
        }
    }
    binding.picturesList.adapter = adapter
    subscribeUi(adapter)

    setHasOptionsMenu(true)
    return binding.root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//    inflater.inflate(R.menu.menu_plant_list, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
//    return when (item.itemId) {
//      R.id.filter_zone -> {
//        updateData()
//        true
//      }
//      else -> super.onOptionsItemSelected(item)
//    }
    return super.onOptionsItemSelected(item)
  }

  private fun subscribeUi(adapter: PictureAdapter) {
    viewModel.pictures.observe(viewLifecycleOwner) { pictures ->
      /**
       *  Plant may return null, but the [observe] extension function assumes it will not be null.
       *  So there will be a warning（Condition `plants != null` is always `true`） here.
       *  I am not sure if the database return data type should be defined as nullable, Such as `LiveData<List<Plant>?>` .
       */
      if (pictures != null) adapter.submitList(pictures)
    }
  }

  private fun updateData() {
//    with(viewModel) {
//      if (isFiltered()) {
//        clearGrowZoneNumber()
//      } else {
//        setGrowZoneNumber(9)
//      }
//    }
  }
}