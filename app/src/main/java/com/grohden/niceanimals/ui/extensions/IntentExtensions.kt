package com.grohden.niceanimals.ui.extensions

import android.content.Intent

fun Intent.putEnumExtra(key: String, enum: Enum<*>) {
    putExtra(key, enum.name)
}

inline fun <reified T : Enum<T>> Intent.getEnumExtra(key: String): T {
    return enumValueOf(getStringExtra(key))
}