package com.coroutinesflow.features.hero_details.data.remote_datastore

import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.features.heroes_home.data.entities.MarvelCharacters
import com.coroutinesflow.frameworks.network.getRemoteDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi

class HeroDetailsRemoteDataStore(
    private val aPIs: APIs,
    private val iODispatcher: CoroutineDispatcher
) {

    //Comics
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterComicsList(characterId: Int) =
        getRemoteDate<MarvelCharacters>(iODispatcher) {
            aPIs.marvelHeroCharacterComicsListSuspend(characterId)
        }

    //Series
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterSeriesList(characterId: Int) =
        getRemoteDate<MarvelCharacters>(iODispatcher) {
            aPIs.marvelHeroCharacterSeriesListSuspend(characterId)
        }

    //Stories
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterStoriesList(characterId: Int) =
        getRemoteDate<MarvelCharacters>(iODispatcher) {
            aPIs.marvelHeroCharacterStoriesListSuspend(characterId)
        }

    //Events
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterEventsList(characterId: Int) =
        getRemoteDate<MarvelCharacters>(iODispatcher) {
            aPIs.marvelHeroCharacterEventsListSuspend(characterId)
        }
}