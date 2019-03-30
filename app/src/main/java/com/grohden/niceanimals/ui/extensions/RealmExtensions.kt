package com.grohden.niceanimals.ui.extensions

import io.realm.RealmQuery

// Supports for ordinal
fun <E> RealmQuery<E>.isEnumOrdinal(fieldName: String, enum: Enum<*>): RealmQuery<E> {
  return equalTo(fieldName, enum.ordinal)
}

// Supports for named enums
fun <E> RealmQuery<E>.isEnum(fieldName: String, enum: Enum<*>): RealmQuery<E> {
  return equalTo(fieldName, enum.name)
}