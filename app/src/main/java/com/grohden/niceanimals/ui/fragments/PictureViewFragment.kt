package com.grohden.niceanimals.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.PagerSnapHelper
import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.databinding.FragmentPictureViewBinding
import com.grohden.niceanimals.ui.adapters.PictureViewAdapter
import com.grohden.niceanimals.ui.viewmodels.PictureListViewModel

class PictureViewFragment : Fragment() {

  private var position: Int = 0
  private val viewModel: PictureListViewModel by viewModels {
    InjectorUtil.providePicturesListViewModelFactory(requireContext())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = FragmentPictureViewBinding.inflate(inflater, container, false)
    context ?: return binding.root

    val adapter = PictureViewAdapter()
    val bundle = PictureViewFragmentArgs.fromBundle(arguments!!)
    position = bundle.position

    PagerSnapHelper().attachToRecyclerView(binding.picturesList)
    binding.picturesList.adapter = adapter
    subscribeUi(adapter, binding)
    return binding.root
  }

  private fun subscribeUi(adapter: PictureViewAdapter, binding: FragmentPictureViewBinding) {
    viewModel.pictures.observe(viewLifecycleOwner) { pictures ->
      if (pictures != null) {
        adapter.submitList(pictures)
        binding.picturesList.scrollToPosition(position)
      }
    }
  }
}