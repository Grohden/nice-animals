package com.grohden.niceanimals.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.grohden.niceanimals.R
import com.grohden.niceanimals.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

  private val navController by lazy {
    findNavController(this, R.id.navHostFragment)
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
          window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        R.id.pictureViewFragment -> {
          supportActionBar?.hide()
          window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
      }
    }
  }

  private fun setupAppBar() {
    setSupportActionBar(appToolbar)

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