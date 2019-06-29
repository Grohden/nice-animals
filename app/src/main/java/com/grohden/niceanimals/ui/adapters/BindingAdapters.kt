package com.grohden.niceanimals.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.grohden.niceanimals.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
  if (!imageUrl.isNullOrEmpty()) {
    Picasso.get()
      .load(imageUrl)
      .placeholder(CircularProgressDrawable(view.context).apply {
        setStyle(CircularProgressDrawable.LARGE)
        setColorSchemeColors(R.color.colorPrimaryDark)
        start()
      })
      .into(view)
  }
}