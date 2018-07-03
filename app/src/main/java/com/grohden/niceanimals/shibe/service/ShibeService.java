package com.grohden.niceanimals.shibe.service;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShibeService {

    /**
     * Endpoint that returns an array with image urls
     *
     * @param animalType type of animal urls you wish for
     * @param count      quantity of image urls, limited to 100
     */
    @GET("{animalType}")
    CompletableFuture<List<URL>> fetchNiceImageUrls(
            @Path("animalType") AnimalType animalType,
            @Query("count") Integer count
    );

}
