package com.grohden.niceanimals

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.grohden.niceanimals.dagger.AppModule
import com.grohden.niceanimals.dagger.NetModule
import com.grohden.niceanimals.dagger.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.realm.Realm
import javax.inject.Inject

class NiceApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        DaggerAppComponent
                .builder()
                .application(this)
                .netModule(NetModule())
                .appModule(AppModule())
                .baseUrl(BASE_SHIBE_URL)
                .build()
                .inject(this)
    }

    companion object {
        private const val BASE_SHIBE_URL = "https://shibe.online/api/"
    }

}
