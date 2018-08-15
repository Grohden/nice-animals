package com.grohden.niceanimals.dagger.components


import android.app.Application
import com.grohden.niceanimals.NiceApplication
import com.grohden.niceanimals.dagger.ApiModule
import com.grohden.niceanimals.dagger.AppModule
import com.grohden.niceanimals.dagger.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApiModule::class), (AppModule::class), (NetModule::class), (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(instance: NiceApplication)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun netModule(netModule: NetModule): Builder

        @BindsInstance
        fun appModule(appModule: AppModule): Builder

        @BindsInstance
        fun baseUrl(@Named("baseUrl") baseUrl: String): Builder
    }
}