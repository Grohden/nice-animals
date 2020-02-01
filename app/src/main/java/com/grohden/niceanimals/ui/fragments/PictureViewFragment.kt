package com.grohden.niceanimals.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.databinding.FragmentPictureViewBinding
import com.grohden.niceanimals.ui.adapters.PictureViewAdapter
import com.grohden.niceanimals.ui.viewmodels.PictureListViewModel

class PictureViewFragment : Fragment() {
  private val args: PictureViewFragmentArgs by navArgs()
  private val viewModel: PictureListViewModel by viewModels {
    InjectorUtil.providePicturesListViewModelFactory(
      requireContext(),
      args.animalType
    )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = FragmentPictureViewBinding.inflate(inflater, container, false)
    context ?: return binding.root

    val adapter = PictureViewAdapter()
    PagerSnapHelper().attachToRecyclerView(binding.picturesList)
    binding.picturesList.adapter = adapter
    subscribeUi(adapter, binding)
    return binding.root
  }

  private fun subscribeUi(adapter: PictureViewAdapter, binding: FragmentPictureViewBinding) {
    viewModel.pictures.observe(viewLifecycleOwner) { pictures ->
      adapter.submitList(pictures)
      binding.picturesList.scrollToPosition(args.position)
    }
  }
}