package com.grohden.niceanimals.services;

import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.shibe.service.AnimalType;
import com.grohden.niceanimals.shibe.service.ShibeService;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.realm.Realm;

public class NiceAnimalsService {
    private static final int DEFAULT_IMAGE_FETCH_COUNT = 4;

    ShibeService shibeService;

    public NiceAnimalsService(ShibeService shibeService) {
        this.shibeService = shibeService;
    }

    private <T> List<T> combineLists(List<T> listOne, List<T> listTwo) {
        return Stream.concat(
                listOne.stream(),
                listTwo.stream()
        ).collect(Collectors.toList());
    }

    private void persistListIntoRealm(List<NiceAnimal> animals) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealm(animals);
            realm.commitTransaction();
        }
    }

    public List<NiceAnimal> buildDogsFromUrlList(List<URL> urlList) {
        return urlList
                .stream()
                .map(URL::toString)
                .map(NiceAnimal::new)
                .collect(Collectors.toList());
    }


    /**
     * Fetches more animals
     *
     * @param type  type of the animal to be fetched
     * @param count quantity of animals
     * @return a completable future with those new animals to chain into another operation
     */
    public CompletableFuture<List<NiceAnimal>> fetchMoreAnimals(AnimalType type, int count) {
        CompletableFuture<List<URL>> future = shibeService.fetchNiceImageUrls(
                type,
                count
        );

        return future.thenApplyAsync(this::buildDogsFromUrlList);
    }

    /**
     * Fetches more animals
     *
     * @param type type of the animal to be fetched
     * @return a completable future with those new animals to chain into another operation
     */
    public CompletableFuture<List<NiceAnimal>> fetchMoreAnimals(AnimalType type) {
        return fetchMoreAnimals(type, DEFAULT_IMAGE_FETCH_COUNT);
    }

    /**
     * Fetches and persists all three types of animals into realm, shuffling them before
     * persisting
     *
     * @return a completable future with those new animals to chain into another operation
     */
    public CompletableFuture<List<NiceAnimal>> fetchAndPersistAllTypes() {

        return fetchMoreAnimals(AnimalType.SHIBES)
                .thenCombineAsync(fetchMoreAnimals(AnimalType.BIRDS), this::combineLists)
                .thenCombineAsync(fetchMoreAnimals(AnimalType.CATS), this::combineLists)
                .thenApply(animals -> {
                    final long seed = System.nanoTime();

                    Collections.shuffle(animals, new Random(seed));

                    persistListIntoRealm(animals);
                    return animals;
                });
    }

    /**
     * Fetches and persists new animals into realm
     *
     * @param type  type of the animal to be fetched and persisted
     * @param count quantity of animals
     * @return a completable future with those new animals to chain into another operation
     */
    public CompletableFuture<List<NiceAnimal>> fetchAndPersistMore(AnimalType type, int count) {

        return fetchMoreAnimals(type, count).thenApplyAsync((animals) -> {
            persistListIntoRealm(animals);
            return animals;
        });
    }

    /**
     * Fetches and persists new animals into realm
     *
     * @param type type of the animal to be fetched and persisted
     * @return a completable future with those new animals to chain into another operation
     */
    public CompletableFuture<List<NiceAnimal>> fetchAndPersistMore(AnimalType type) {
        return fetchAndPersistMore(type, DEFAULT_IMAGE_FETCH_COUNT);
    }
}
