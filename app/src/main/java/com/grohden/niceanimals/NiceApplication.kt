package com.grohden.niceanimals

import android.app.Application

class NiceApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    // startKoin(this, niceApp)
  }
}
