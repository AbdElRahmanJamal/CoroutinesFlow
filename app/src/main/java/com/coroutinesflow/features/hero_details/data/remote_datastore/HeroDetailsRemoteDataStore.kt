package com.coroutinesflow.features.hero_details.data.remote_datastore

import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.frameworks.network.NetworkHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi

class HeroDetailsRemoteDataStore(
    private val networkHandler: NetworkHandler<MarvelCharacters>,
    private val aPIs: APIs,
    private val iODispatcher: CoroutineDispatcher
) {

    //Comics
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterComicsList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID, iODispatcher) {
            aPIs.marvelHeroCharacterComicsListSuspend(characterId)
        }

    //Series
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterSeriesList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID, iODispatcher) {
            aPIs.marvelHeroCharacterSeriesListSuspend(characterId)
        }

    //Stories
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterStoriesList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID, iODispatcher) {
            aPIs.marvelHeroCharacterStoriesListSuspend(characterId)
        }

    //Events
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterEventsList(apiID: String, characterId: Int) =
        networkHandler.callAPI(apiID, iODispatcher) {
            aPIs.marvelHeroCharacterEventsListSuspend(characterId)
        }

    fun cancelAPICall(apiID: String) = networkHandler.cancelJob(apiID)

}