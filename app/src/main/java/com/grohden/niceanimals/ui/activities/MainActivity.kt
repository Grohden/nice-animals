package com.grohden.niceanimals.ui.activities

import android.os.Bundle
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
        setTheme(R.style.SplashScreenTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAppBar()
        setupNavigationListener()
    }

    private fun setupNavigationListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashScreenFragment -> {
                    supportActionBar?.hide()
                }
                R.id.contentTabsFragment -> {
                    supportActionBar?.show()
                }
                R.id.galleryFragment -> {
                    supportActionBar?.hide()
                }
            }
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(appToolbar)

        val appBarConfiguration = AppBarConfiguration.Builder(
            setOf(
                R.id.contentTabsFragment,
                R.id.galleryFragment
            )
        ).build()

        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            appBarConfiguration
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}