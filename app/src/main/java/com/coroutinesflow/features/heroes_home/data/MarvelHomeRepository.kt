package com.coroutinesflow.features.heroes_home.data

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHomeTable
import com.coroutinesflow.features.heroes_home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.heroes_home.data.remote_datastore.MarvelHomeRemoteDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class MarvelHomeRepository(
    private val marvelHomeRemoteDataStore: MarvelHomeRemoteDataStore,
    private val marvelHomeLocalDataStore: MarvelHomeLocalDataStore,
    private val iODispatcher: CoroutineDispatcher
) {

    @ExperimentalCoroutinesApi
    fun getListOfMarvelHeroesCharacters(
        apiID: String,
        limit: Int,
        offset: Int,
        homeID: String
    ) =
        flow {
            val listOfMarvelHeroesCharactersLocal: MarvelHomeTable =
                marvelHomeLocalDataStore.getListOfMarvelHeroesCharacters(limit, offset, homeID)

            if (listOfMarvelHeroesCharactersLocal != null && !listOfMarvelHeroesCharactersLocal.listOfHeroes.isNullOrEmpty()) {
                emit(APIState.DataStat(listOfMarvelHeroesCharactersLocal.listOfHeroes))
            } else {
                marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(apiID, limit, offset)
                    .collect { states ->
                        when (states) {
                            is APIState.LoadingState -> emit(APIState.LoadingState)
                            is APIState.ErrorState -> emit(APIState.ErrorState(states.throwable))
                            is APIState.DataStat -> {
                                emit(APIState.DataStat(states.value.data.results))
                                marvelHomeLocalDataStore.updateInsertMarvelHeroesCharacters(
                                    MarvelHomeTable(homeID, states.value.data.results)
                                )
                            }
                        }
                    }
            }
        }.onStart { emit(APIState.LoadingState) }.flowOn(iODispatcher)

    fun cancelAPICall(apiID: String) = marvelHomeRemoteDataStore.cancelAPICall(apiID)

}