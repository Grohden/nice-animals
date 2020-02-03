package com.grohden.niceanimals.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.grohden.niceanimals.AppConstants
import com.grohden.niceanimals.base.di.manual.InjectorUtil
import com.grohden.niceanimals.databinding.FragmentPictureListBinding
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.adapters.PictureAdapter
import com.grohden.niceanimals.ui.viewmodels.PictureListViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
private data class PicturesListFragmentArgs(
  val type: AnimalType
) : Parcelable

class PicturesListFragment : Fragment() {
  private lateinit var args: PicturesListFragmentArgs

  private val viewModel: PictureListViewModel by viewModels {
    InjectorUtil.providePicturesListViewModelFactory(requireContext(), args.type)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    arguments!!.apply {
      args = getParcelable(ARGS)!!
    }

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

    return binding.root
  }

  private fun subscribeUi(adapter: PictureAdapter) {
    viewModel.pictures.observe(viewLifecycleOwner, adapter::submitList)
  }

  companion object {
    private const val ARGS = "args"
    val githubUrl: Uri = Uri.parse(AppConstants.PROJECT_URL)

    fun create(type: AnimalType): PicturesListFragment {
      return PicturesListFragment().apply {
        arguments = Bundle().apply {
          putParcelable(ARGS, PicturesListFragmentArgs(type))
        }
      }
    }
  }
}
