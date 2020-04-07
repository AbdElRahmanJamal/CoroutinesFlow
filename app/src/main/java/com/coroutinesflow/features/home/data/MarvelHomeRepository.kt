package com.coroutinesflow.features.home.data

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.home.data.remote_datastore.MarvelHomeRemoteDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class MarvelHomeRepository(
    private val marvelHomeRemoteDataStore: MarvelHomeRemoteDataStore,
    private val marvelHomeLocalDataStore: MarvelHomeLocalDataStore,
    private val iODispatcher: CoroutineDispatcher
) {

    @ExperimentalCoroutinesApi
    suspend fun getListOfMarvelHeroesCharacters(limit: Int = 10, offset: Int = 0) = flow {

            marvelHomeLocalDataStore.getListOfMarvelHeroesCharacters(limit, offset).collect {

                if (it.isNotEmpty()) {
                    emit(APIState.DataStat(it))
                } else {
                    marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(limit, offset)
                        .collect { states ->
                            when (states) {
                                is APIState.LoadingState -> emit(APIState.LoadingState)
                                is APIState.ErrorState -> emit(APIState.ErrorState(states.exception))
                                is APIState.DataStat -> {
                                    emit(APIState.DataStat(states.value.data.results))
                                    marvelHomeLocalDataStore.updateInsert(states.value.data.results)
                                }
                            }
                        }
                }
        }
    }.onStart { emit(APIState.LoadingState) }.flowOn(iODispatcher)

}