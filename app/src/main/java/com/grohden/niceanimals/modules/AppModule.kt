package com.grohden.niceanimals.modules

import android.app.Application
import com.grohden.niceanimals.services.NiceAnimalsService
import com.grohden.niceanimals.shibe.service.ShibeService
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class AppModule(internal var mApplication: Application) {

    @Provides
    @Singleton
    internal fun provideRealmConfiguration(): RealmConfiguration {
        val builder = RealmConfiguration.Builder()

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun providesRealm(configuration: RealmConfiguration): Realm {
        Realm.setDefaultConfiguration(configuration)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    internal fun providesNiceAnimalService(shibeService: ShibeService): NiceAnimalsService {
        return NiceAnimalsService(shibeService)
    }

    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return mApplication
    }

}
