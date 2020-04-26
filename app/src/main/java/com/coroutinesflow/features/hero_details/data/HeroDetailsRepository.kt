package com.coroutinesflow.features.hero_details.data

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.hero_details.data.entities.MarvelHeroDetailsTable
import com.coroutinesflow.features.hero_details.data.local_datastore.HeroDetailsLocalDataStore
import com.coroutinesflow.features.hero_details.data.remote_datastore.HeroDetailsRemoteDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class HeroDetailsRepository(
    private val heroDetailsRemoteDataStore: HeroDetailsRemoteDataStore
    , private val heroDetailsLocalDataStore: HeroDetailsLocalDataStore,
    private val iODispatcher: CoroutineDispatcher
) {
    //Comics
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterComicsList(apiID: String, sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(apiID, characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }.onStart { emit(APIState.LoadingState) }.flowOn(iODispatcher)

    //Series
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterSeriesList(apiID: String, sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(apiID, characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }.onStart { emit(APIState.LoadingState) }.flowOn(iODispatcher)


    //Stories
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterStoriesList(apiID: String, sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(apiID, characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }.onStart { emit(APIState.LoadingState) }.flowOn(iODispatcher)

    //Events
    @ExperimentalCoroutinesApi
    fun marvelHeroCharacterEventsList(apiID: String, sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(apiID, characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }.onStart { emit(APIState.LoadingState) }.flowOn(iODispatcher)

    fun cancelAPICall(apiID: String) = heroDetailsRemoteDataStore.cancelAPICall(apiID)


    private suspend fun handleOnStateChange(
        state: APIState<MarvelCharacters>,
        sectionID: String,
        characterId: Int
    ) {
        if (state is APIState.DataStat) {
            heroDetailsLocalDataStore.insertRemoteDataIntoDB(sectionID, characterId, state)
        }
    }


    private fun emitUIModel(state: APIState<MarvelCharacters>) =
        when (state) {
            is APIState.LoadingState -> APIState.LoadingState
            is APIState.ErrorState -> APIState.ErrorState(state.throwable)
            is APIState.DataStat -> {
                APIState.DataStat(state.value.data.results)
            }
        }
}