package com.coroutinesflow.features.hero_details.data

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsSectionsID
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

    @ExperimentalCoroutinesApi
    fun getMarvelHeroCharacterDetailsSectionList(
        apiID: String,
        sectionID: HeroDetailsSectionsID,
        characterId: Int
    ) =
        flow {

            val sectionsIDName = sectionID.name

            val marvelDetailsSection: MarvelHeroDetailsTable =
                heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(
                    sectionsIDName,
                    characterId
                )

            if (marvelDetailsSection != null && !marvelDetailsSection.heroSectionList.isNullOrEmpty()) {
                emit(APIState.DataStat(marvelDetailsSection.heroSectionList))
            } else {

                val marvelHeroCharacterComicsList = when (sectionID) {
                    HeroDetailsSectionsID.COMICS -> heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(
                        apiID,
                        characterId
                    )
                    HeroDetailsSectionsID.STORIES -> heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(
                        apiID,
                        characterId
                    )
                    HeroDetailsSectionsID.SERIES -> heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(
                        apiID,
                        characterId
                    )
                    HeroDetailsSectionsID.EVENTS -> heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(
                        apiID,
                        characterId
                    )
                }
                marvelHeroCharacterComicsList.collect { state ->
                    emit(emitUIModel(state))
                    handleOnStateChange(state, sectionsIDName, characterId)
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