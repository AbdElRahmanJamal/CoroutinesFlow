package com.coroutinesflow.features.hero_details.data

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.hero_details.data.local_datastore.HeroDetailsLocalDataStore
import com.coroutinesflow.features.hero_details.data.remote_datastore.HeroDetailsRemoteDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onStart

class HeroDetailsRepository(
    private val heroDetailsRemoteDataStore: HeroDetailsRemoteDataStore
    , private val heroDetailsLocalDataStore: HeroDetailsLocalDataStore
) {
    //Comics
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterComicsList(characterId: Int) =
        heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(characterId)

    //Series
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterSeriesList(characterId: Int) =
        heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(characterId)

    //Stories
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterStoriesList(characterId: Int) =
        heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(characterId)

    //Events
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterEventsList(characterId: Int) =
        heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(characterId)
}