package com.grohden.niceanimals.realm.entities

import com.grohden.niceanimals.shibe.service.AnimalType
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class NiceAnimal : RealmObject {

    @PrimaryKey
    lateinit var pictureUrl: String
    lateinit var type: String

    constructor() {

    }

    constructor(pictureUrl: String, animalType: AnimalType) {
        this.pictureUrl = pictureUrl
        this.type = animalType.name
    }
}
