package com.grohden.niceanimals

import android.app.Application

import com.grohden.niceanimals.components.DaggerNetComponent
import com.grohden.niceanimals.components.NetComponent
import com.grohden.niceanimals.modules.AppModule
import com.grohden.niceanimals.modules.NetModule

import io.realm.Realm

class NiceApplication : Application() {

    lateinit var mNetComponent: NetComponent

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        mNetComponent = DaggerNetComponent
                .builder()
                .netModule(NetModule(BASE_SHIBE_URL))
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        private const val BASE_SHIBE_URL = "http://shibe.online/api/"
    }

}
