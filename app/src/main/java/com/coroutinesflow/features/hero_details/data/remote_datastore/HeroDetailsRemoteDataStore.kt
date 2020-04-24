package com.coroutinesflow.features.hero_details.data.remote_datastore

import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.frameworks.network.NetworkHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi

class HeroDetailsRemoteDataStore(
    private val networkHandler: NetworkHandler<MarvelCharacters>,
    private val aPIs: APIs
) {

    //Comics
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterComicsList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID) {
            aPIs.marvelHeroCharacterComicsListSuspend(characterId)
        }

    //Series
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterSeriesList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID) {
            aPIs.marvelHeroCharacterSeriesListSuspend(characterId)
        }

    //Stories
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterStoriesList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID) {
            aPIs.marvelHeroCharacterStoriesListSuspend(characterId)
        }

    //Events
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterEventsList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID) {
            aPIs.marvelHeroCharacterEventsListSuspend(characterId)
        }

    fun cancelAPICall(apiID: String) = networkHandler.cancelJob(apiID)

}