package com.grohden.niceanimals.services;

import com.grohden.niceanimals.realm.entities.NiceAnimal;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class NiceAnimalsService {

    public List<NiceAnimal> buildDogsFromUrlList(List<URL> urlList) {
        return urlList
                .stream()
                .map(URL::toString)
                .map(NiceAnimal::new)
                .collect(Collectors.toList());
    }
}
