package com.grohden.niceanimals.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.fragments.PicturesListFragment

const val SHIBES_PAGE_INDEX = 0
const val CATS_PAGE_INDEX = 1
const val BIRDS_PAGE_INDEX = 2

class NiceAnimalsPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  private val tabFragmentCreators = mapOf(
    SHIBES_PAGE_INDEX to {
      PicturesListFragment.create(AnimalType.Shibe)
    },
    CATS_PAGE_INDEX to {
      PicturesListFragment.create(AnimalType.Cat)
    },
    BIRDS_PAGE_INDEX to {
      PicturesListFragment.create(AnimalType.Bird)
    }
  )

  override fun getItemCount(): Int = tabFragmentCreators.size

  override fun createFragment(position: Int): Fragment =
    tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}
