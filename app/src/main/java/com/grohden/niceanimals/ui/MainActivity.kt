package com.grohden.niceanimals.ui

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.grohden.niceanimals.R
import com.grohden.niceanimals.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

  private val navController by lazy {
    findNavController(this, R.id.nav_host_fragment)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupAppBar()
    setupNavigationListener()
  }

  private fun setupNavigationListener() {
    navController.addOnDestinationChangedListener { _, destination, _ ->
      when (destination.id) {
        R.id.homeViewPagerFragment -> {
          supportActionBar?.hide()
        }
        R.id.pictureViewFragment -> {
          supportActionBar?.hide()
        }
      }
    }
  }

  private fun setupAppBar() {
    setSupportActionBar(app_toolbar)

    val appBarConfiguration = AppBarConfiguration.Builder(
      setOf(
        R.id.homeViewPagerFragment,
        R.id.pictureViewFragment
      )
    ).build()

    NavigationUI.setupActionBarWithNavController(
      this,
      navController,
      appBarConfiguration
    )
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }
}
