package com.grohden.niceanimals.components

import com.grohden.niceanimals.modules.AppModule
import com.grohden.niceanimals.modules.NetModule
import com.grohden.niceanimals.ui.activities.MainActivity
import com.grohden.niceanimals.ui.activities.SplashScreenActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetModule::class), (AppModule::class)])
interface NetComponent {
    fun inject(activity: SplashScreenActivity)

    fun inject(activity: MainActivity)
}
