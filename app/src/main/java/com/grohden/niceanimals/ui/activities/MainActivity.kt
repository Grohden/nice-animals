package com.grohden.niceanimals.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import blade.Blade
import com.grohden.niceanimals.AppConstants
import com.grohden.niceanimals.R
import com.grohden.niceanimals.ui.adapters.TabPageAdapter
import com.grohden.niceanimals.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

@Blade
class MainActivity : BaseActivity() {

    companion object {
        val githubUrl: Uri = Uri.parse(AppConstants.PROJECT_URL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appToolbar)
        configureTabs()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_github -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, githubUrl)
                startActivity(browserIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureTabs() {
        animalGridPager.adapter = TabPageAdapter(
            supportFragmentManager,
            this
        )

        animalTypeTab.setupWithViewPager(animalGridPager)
    }
}
