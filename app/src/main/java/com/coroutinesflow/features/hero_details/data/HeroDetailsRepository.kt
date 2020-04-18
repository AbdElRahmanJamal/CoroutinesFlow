package com.coroutinesflow.features.hero_details.data

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.hero_details.data.entities.MarvelHeroDetailsTable
import com.coroutinesflow.features.hero_details.data.local_datastore.HeroDetailsLocalDataStore
import com.coroutinesflow.features.hero_details.data.remote_datastore.HeroDetailsRemoteDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class HeroDetailsRepository(
    private val heroDetailsRemoteDataStore: HeroDetailsRemoteDataStore
    , private val heroDetailsLocalDataStore: HeroDetailsLocalDataStore
) {
    //Comics
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterComicsList(sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }

    //Series
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterSeriesList(sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }


    //Stories
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterStoriesList(sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }

    //Events
    @ExperimentalCoroutinesApi
    suspend fun marvelHeroCharacterEventsList(sectionID: String, characterId: Int) =
        flow {

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterId)

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {
                heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(characterId)
                    .collect { state ->
                        emit(emitUIModel(state))
                        handleOnStateChange(state, sectionID, characterId)
                    }
            }
        }

    private suspend fun handleOnStateChange(
        state: APIState<MarvelCharacters>,
        sectionID: String,
        characterId: Int
    ) {
        if (state is APIState.DataStat) {
            heroDetailsLocalDataStore.updateInsertMarvelDetails(
                MarvelHeroDetailsTable(
                    sectionID,
                    characterId,
                    state.value.data.results
                )
            )
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