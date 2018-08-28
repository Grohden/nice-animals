package com.grohden.niceanimals.dagger

import com.grohden.niceanimals.ui.activities.GalleryActivity
import com.grohden.niceanimals.ui.activities.MainActivity
import com.grohden.niceanimals.ui.activities.SplashScreenActivity
import com.grohden.niceanimals.ui.fragments.AnimalGridFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ApiModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeGalleryActivityInjector(): GalleryActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashScreenActivityInjector(): SplashScreenActivity

    @ContributesAndroidInjector
    abstract fun contributeAnimalGridFragmentInjector(): AnimalGridFragment

}