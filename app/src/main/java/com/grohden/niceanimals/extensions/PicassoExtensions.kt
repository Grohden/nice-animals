package com.grohden.niceanimals.extensions

import android.widget.ImageView
import com.grohden.niceanimals.listeners.FinishCallback
import com.squareup.picasso.RequestCreator

fun RequestCreator.at(target: ImageView, onFinish: (() -> Unit)) {
  into(target, FinishCallback(onFinish))
}
