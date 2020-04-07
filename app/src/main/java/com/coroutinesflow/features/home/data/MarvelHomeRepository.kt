package com.coroutinesflow.features.home.data

import com.coroutinesflow.features.home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.home.data.remote_datastore.MarvelHomeRemoteDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MarvelHomeRepository(
    private val marvelHomeRemoteDataStore: MarvelHomeRemoteDataStore,
    private val marvelHomeLocalDataStore: MarvelHomeLocalDataStore
) {

    @ExperimentalCoroutinesApi
    suspend fun getListOfMarvelHeroesCharacters(limit: Int = 10, offset: Int = 0) =
        marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(limit, offset)
}