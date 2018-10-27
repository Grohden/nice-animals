package com.grohden.niceanimals.services

import android.util.Log
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.shibe.service.AnimalType
import com.grohden.niceanimals.shibe.service.ShibeService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import java.net.URL

class NiceAnimalsService(private var shibeService: ShibeService) {

    private fun persistListIntoRealm(animals: List<NiceAnimal>) {
        try {
            val realm: Realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            realm.copyToRealm(animals)
            realm.commitTransaction()
            realm.close()
        } catch (e: Exception) {
            Log.e("NiceAnimalsService", "Hey, i couldn't save the animals :/", e)
        }
    }

    private fun buildAnimalsFromList(urlList: List<URL>, type: AnimalType): List<NiceAnimal> {
        return urlList
                .map { it.toString() }
                .map { NiceAnimal(it, type) }
                .toList()
    }

    /**
     * Fetches all types of animals from shibe api in parallel
     *
     * @return a single with those new animals to chain into another operation
     */
    private fun fetchAllTypes(): Single<List<NiceAnimal>> {
        return Single.zip(
                fetchMoreAnimals(AnimalType.shibes),
                fetchMoreAnimals(AnimalType.birds),
                fetchMoreAnimals(AnimalType.cats),
                Function3<List<NiceAnimal>, List<NiceAnimal>, List<NiceAnimal>, List<NiceAnimal>>
                { shibes, birds, cats -> (shibes + birds + cats) }
        )
    }

    /**
     * Fetches more animals and remove all the old ones before putting the new ones into
     * realm
     *
     * @param type type of the animal to be fetched
     * @param count quantity of animals
     *
     * @return a completable future with those new animals to chain into another operation
     */
    fun refreshAnimalType(type: AnimalType, count: Int = DEFAULT_IMAGE_FETCH_COUNT): Single<List<NiceAnimal>> {
        return fetchMoreAnimals(type, count)
                .map { animals ->
                    Realm.getDefaultInstance().executeTransaction { realm ->
                        realm.where(NiceAnimal::class.java)
                                .equalTo("type", type.name)
                                .findAll()
                                .deleteAllFromRealm()

                        realm.copyToRealm(animals.shuffled())
                    }
                    animals
                }
    }

    /**
     * Fetches more animals
     *
     * @param type type of the animal to be fetched
     * @param count quantity of animals
     * @return a observable to receive the values from
     */
    private fun fetchMoreAnimals(type: AnimalType, count: Int = DEFAULT_IMAGE_FETCH_COUNT): Single<List<NiceAnimal>> {
        val future = shibeService.fetchNiceImageUrls(type, count)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

        return future.map { buildAnimalsFromList(it, type) }
    }

    /**
     * Fetches and persists all three types of animals into realm, shuffling them before
     * persisting
     *
     * @return a observable with those new animals to chain into another operation
     */
    fun fetchAndPersistAllTypes(): Single<List<NiceAnimal>> {

        return fetchAllTypes()
                .map { animals ->
                    persistListIntoRealm(animals.shuffled())
                    animals
                }
    }

    /**
     * Fetches and persists new animals into realm
     *
     * @param type type of the animal to be fetched and persisted
     * @param count quantity of animals
     * @return a completable future with those new animals to chain into another operation
     */
    fun fetchAndPersistMore(type: AnimalType, count: Int = DEFAULT_IMAGE_FETCH_COUNT): Single<List<NiceAnimal>> {

        return fetchMoreAnimals(type, count).map { animals ->
            persistListIntoRealm(animals)
            animals
        }
    }

    companion object {
        private const val DEFAULT_IMAGE_FETCH_COUNT = 10
    }
}