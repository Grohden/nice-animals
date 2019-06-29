package com.grohden.niceanimals.ui.fragments

import android.content.Intent
import android.net.Uri
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
import com.grohden.niceanimals.AppConstants
import com.grohden.niceanimals.R
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
    inflater.inflate(R.menu.main_menu, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_github -> {
        val browserIntent = Intent(
          Intent.ACTION_VIEW,
          githubUrl
        )
        startActivity(browserIntent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun subscribeUi(adapter: PictureAdapter) {
    viewModel.pictures.observe(viewLifecycleOwner) { pictures ->
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

  companion object {
    val githubUrl: Uri = Uri.parse(AppConstants.PROJECT_URL)
  }
}