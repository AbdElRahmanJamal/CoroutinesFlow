package com.coroutinesflow.features.home.data.remote_datastore

import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.features.home.data.model.MarvelCharacters
import com.coroutinesflow.frameworks.network.getRemoteDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MarvelHomeRemoteDataStore(
    private val aPIs: APIs,
    private val iODispatcher: CoroutineDispatcher
) {

    @ExperimentalCoroutinesApi
    suspend fun getListOfMarvelHeroesCharacters(limit: Int = 10, offset: Int = 0) =
        getRemoteDate<MarvelCharacters>(iODispatcher) {
            aPIs.getMarvelCharactersSuspend(limit, offset)
        }
}