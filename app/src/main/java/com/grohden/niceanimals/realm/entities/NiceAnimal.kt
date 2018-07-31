package com.grohden.niceanimals.realm.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class NiceAnimal : RealmObject {

    @PrimaryKey
    var pictureUrl: String? = null
    //    private AnimalType type; TODO: find out a patter for enums in realm

    constructor() {

    }

    constructor(pictureUrl: String) {
        this.pictureUrl = pictureUrl
    }
}
