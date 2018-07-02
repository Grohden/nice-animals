package com.grohden.niceanimals.modules;

import android.app.Application;

import com.grohden.niceanimals.services.NiceAnimalsService;

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
        return Realm.getInstance(configuration);
    }

    @Provides
    @Singleton
    NiceAnimalsService providesNiceAnimalService() {
        return new NiceAnimalsService();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

}
