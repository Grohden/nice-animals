package com.grohden.niceanimals.shibe.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL

interface ShibeService {

  /**
   * Endpoint that returns an array with image urls
   *
   * @param animalType type of animal urls you wish for
   * @param count quantity of image urls, limited to 100
   */
  @GET("{animalType}")
  fun fetchNiceImageUrls(
    @Path("animalType") animalType: AnimalType,
    @Query("count") count: Int?
  ): Single<List<String>>
}
