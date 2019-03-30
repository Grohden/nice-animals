package com.grohden.niceanimals.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.grohden.niceanimals.shibe.service.AnimalType

class TabPageAdapter(manager: FragmentManager, val context: Context) :
  FragmentStatePagerAdapter(manager) {
  private companion object {
    val typesMap = mapOf(
      "Cats" to AnimalType.cats,
      "Shibes" to AnimalType.shibes,
      "Birbs" to AnimalType.birds
    )
  }

  override fun getItem(position: Int): Fragment {
    return blade.F.newAnimalGridFragment(
      typesMap.values.elementAt(position)
    )
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return typesMap.keys.elementAt(position)
  }

  override fun getCount(): Int = typesMap.size
}