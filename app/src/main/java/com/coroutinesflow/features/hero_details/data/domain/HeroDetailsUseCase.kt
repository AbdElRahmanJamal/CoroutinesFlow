package com.coroutinesflow.features.hero_details.data.domain

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.heroes_home.data.entities.MarvelCharacters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class HeroDetailsUseCase(
    private val heroDetailsRepository: HeroDetailsRepository,
    private val iODispatcher: CoroutineDispatcher
) {


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataComics(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterComicsList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher)


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataStories(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterStoriesList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher)


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataSeries(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterSeriesList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher)


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataEvents(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterEventsList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher)


    private fun convertDataToTargetData(state: APIState<MarvelCharacters>) =
        when (state) {
            is APIState.LoadingState -> state
            is APIState.ErrorState -> state
            is APIState.DataStat -> APIState.DataStat(state.value.data.results)
        }
}
