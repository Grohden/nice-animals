package com.grohden.niceanimals.realm.entities;

import io.realm.RealmObject;

public class NiceAnimal extends RealmObject {

    private String pictureUrl;
    private boolean isFavorite = false;
//    private AnimalType type; TODO: find out a patter for enums in realm

    public NiceAnimal() {

    }

    public NiceAnimal(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
