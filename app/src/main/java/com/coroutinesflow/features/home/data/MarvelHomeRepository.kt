package com.coroutinesflow.features.home.data

import kotlinx.coroutines.ExperimentalCoroutinesApi

class MarvelHomeRepository(
    private val marvelHomeRemoteDataStore: MarvelHomeRemoteDataStore,
    private val marvelHomeLocalDataStore: MarvelHomeLocalDataStore
) {

    @ExperimentalCoroutinesApi
    suspend fun getListOfMarvelHeroesCharacters(limit: Int = 10, offset: Int = 0) =
        marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(limit, offset)
}