package com.coroutinesflow.features.heroes_home.data.remote_datastore

import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.frameworks.network.NetworkHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MarvelHomeRemoteDataStore(
    private val networkHandler: NetworkHandler<MarvelCharacters>,
    private val aPIs: APIs,
    private val iODispatcher: CoroutineDispatcher
) {

    @ExperimentalCoroutinesApi
    suspend fun getListOfMarvelHeroesCharacters(apiID: String, limit: Int = 10, offset: Int = 0) =
        networkHandler.getRemoteDate(apiID, iODispatcher) {
            aPIs.getMarvelCharactersSuspend(limit, offset)
        }

    fun cancelAPICall(apiID: String) = networkHandler.cancelJob(apiID)

}