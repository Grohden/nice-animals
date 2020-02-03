package com.grohden.niceanimals.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.grohden.niceanimals.R
import com.grohden.niceanimals.databinding.FragmentViewPagerBinding
import com.grohden.niceanimals.ui.adapters.BIRDS_PAGE_INDEX
import com.grohden.niceanimals.ui.adapters.CATS_PAGE_INDEX
import com.grohden.niceanimals.ui.adapters.NiceAnimalsPageAdapter
import com.grohden.niceanimals.ui.adapters.SHIBES_PAGE_INDEX

class HomeViewPagerFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
    val tabLayout = binding.tabs
    val viewPager = binding.viewPager

    viewPager.adapter = NiceAnimalsPageAdapter(this)

    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
      tab.text = getTabTitle(position)
    }.attach()

    setupToolbarAndMenu(binding)
    return binding.root
  }

  private fun setupToolbarAndMenu(binding: FragmentViewPagerBinding) {
    val mainActivity = (activity as AppCompatActivity)
    mainActivity.setSupportActionBar(binding.toolbar)
    mainActivity.supportActionBar?.setDisplayShowTitleEnabled(true)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.main_menu, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_github -> {
        val browserIntent = Intent(
          Intent.ACTION_VIEW,
          PicturesListFragment.githubUrl
        )
        startActivity(browserIntent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun getTabTitle(position: Int): String? {
    return when (position) {
      SHIBES_PAGE_INDEX -> getString(R.string.shibes_tab_title)
      CATS_PAGE_INDEX -> getString(R.string.cats_tab_title)
      BIRDS_PAGE_INDEX -> getString(R.string.birds_tab_title)
      else -> null
    }
  }
}
