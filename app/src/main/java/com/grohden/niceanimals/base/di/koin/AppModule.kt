package com.grohden.niceanimals.base.di.koin

import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.dsl.module.module

// just declare it
val appModule = module {
    single {
        createRealmConfig()
    }
    factory {
        Realm.getDefaultInstance()
    }
}

fun createRealmConfig(): RealmConfiguration {
    return RealmConfiguration
            .Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
}

val niceApp = listOf(appModule, netModule)