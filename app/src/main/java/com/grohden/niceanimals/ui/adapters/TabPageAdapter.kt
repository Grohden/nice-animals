package com.grohden.niceanimals.ui.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.ui.fragments.AnimalGridFragment

class TabPageAdapter(manager: FragmentManager, val context: Context) : FragmentPagerAdapter(manager) {
    private companion object {
        val typesMap = mapOf(
                "Cats" to AnimalType.cats,
                "Shibes" to AnimalType.shibes,
                "Birbs" to AnimalType.birds
        )
    }

    override fun getItem(position: Int): Fragment {
        return AnimalGridFragment.newInstance(
                typesMap.values.elementAt(position)
        )
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return typesMap.keys.elementAt(position)
    }

    override fun getCount(): Int = typesMap.size
}