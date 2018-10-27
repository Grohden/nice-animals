package com.grohden.niceanimals

import android.app.Application
import com.grohden.niceanimals.base.di.koin.niceApp
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class NiceApplication : Application() {

    private val realmConfig: RealmConfiguration by inject()

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin(this, niceApp)

        Realm.setDefaultConfiguration(realmConfig)
    }
}
