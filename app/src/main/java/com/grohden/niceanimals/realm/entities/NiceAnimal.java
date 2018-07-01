package com.grohden.niceanimals.realm.entities;

import java.net.URL;

import io.realm.RealmObject;

public class NiceAnimal extends RealmObject {
    public String picUrl;
    public boolean isFavorite = false;
//    public NiceAnimalType type;

    public NiceAnimal() {

    }

    public NiceAnimal(URL url) {
        this.picUrl = url.toString();
    }
}
