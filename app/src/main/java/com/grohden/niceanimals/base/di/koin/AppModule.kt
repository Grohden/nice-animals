package com.grohden.niceanimals.base.di.koin

import org.koin.dsl.module.module

val appModule = module {
}

val niceApp = listOf(appModule, netModule)
