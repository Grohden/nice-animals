package com.grohden.niceanimals.services;

import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.shibe.service.AnimalType;
import com.grohden.niceanimals.shibe.service.ShibeService;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import io.realm.Realm;

public class NiceAnimalsService {

    ShibeService shibeService;

    public NiceAnimalsService(ShibeService shibeService) {
        this.shibeService = shibeService;
    }

    public List<NiceAnimal> buildDogsFromUrlList(List<URL> urlList) {
        return urlList
                .stream()
                .map(URL::toString)
                .map(NiceAnimal::new)
                .collect(Collectors.toList());
    }


    /**
     * Fetches and persists new animals into realm
     *
     * @return a completable future with those new animals to chain into another operation
     */
    public CompletableFuture<List<NiceAnimal>> fetchAndPersistMoreAnimals(AnimalType type) {
        CompletableFuture<List<URL>> future = shibeService.fetchNiceImageUrls(
                type,
                10
        );

        return future.thenApplyAsync((dogsList) -> {
            List<NiceAnimal> animals = buildDogsFromUrlList(dogsList);

            try (Realm realm = Realm.getDefaultInstance()) {
                realm.beginTransaction();
                realm.copyToRealm(animals);
                realm.commitTransaction();
            }

            return animals;
        });
    }
}
