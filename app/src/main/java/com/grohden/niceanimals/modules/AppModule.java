package com.grohden.niceanimals.modules;

import android.app.Application;

import com.grohden.niceanimals.services.NiceAnimalsService;
import com.grohden.niceanimals.shibe.service.ShibeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        final RealmConfiguration.Builder builder = new RealmConfiguration
                .Builder();

        return builder.build();
    }

    @Provides
    @Singleton
    Realm providesRealm(RealmConfiguration configuration) {
        Realm.deleteRealm(configuration);
        return Realm.getInstance(configuration);
    }

    @Provides
    @Singleton
    NiceAnimalsService providesNiceAnimalService(ShibeService shibeService) {
        return new NiceAnimalsService(shibeService);
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

}
