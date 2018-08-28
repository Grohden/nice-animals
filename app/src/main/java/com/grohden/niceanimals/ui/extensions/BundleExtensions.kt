package com.grohden.niceanimals.ui.extensions

import android.os.Bundle

fun Bundle.putEnum(key: String, enum: Enum<*>) {
    putString(key, enum.name)
}

inline fun <reified T : Enum<T>> Bundle.getEnum(key: String): T {
    return enumValueOf(getString(key))
}