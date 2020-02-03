package com.grohden.niceanimals.listeners

import com.squareup.picasso.Callback

class FinishCallback(
  private val onFinish: (() -> Unit)
) : Callback {

  override fun onSuccess() {
    onFinish()
  }

  override fun onError(e: Exception?) {
    onFinish()
  }
}
