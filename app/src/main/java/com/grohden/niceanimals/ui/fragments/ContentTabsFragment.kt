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
import com.grohden.niceanimals.AppConstants
import com.grohden.niceanimals.R
import com.grohden.niceanimals.ui.adapters.TabPageAdapter
import com.grohden.niceanimals.ui.base.BaseFragment
import kotlinx.android.synthetic.main.main_content.*

class ContentTabsFragment : BaseFragment() {

    companion object {
        val githubUrl: Uri = Uri.parse(AppConstants.PROJECT_URL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        configureTabs()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_github -> {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    githubUrl
                )
                startActivity(browserIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureTabs() {
        animalGridPager.adapter = TabPageAdapter(
            requireFragmentManager(),
            requireContext()
        )

        animalTypeTab.setupWithViewPager(animalGridPager)
    }
}
