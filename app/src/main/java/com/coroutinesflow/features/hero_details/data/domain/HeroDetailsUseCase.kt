package com.coroutinesflow.features.hero_details.data.domain

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsPageUIModel
import com.coroutinesflow.base.data.entities.MarvelCharacters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class HeroDetailsUseCase(
    private val heroDetailsRepository: HeroDetailsRepository,
    private val iODispatcher: CoroutineDispatcher
) {


    //i may use this function to call all apis in parallel
    //this not used for now
    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataComicsStoriesSeriesEvents(characterId: Int) = flow {
        val heroDetailsPageUIModel =
            awaitAll(
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterComicsList(
                        characterId
                    )
                },
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterStoriesList(
                        characterId
                    )
                },
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterSeriesList(
                        characterId
                    )
                },
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterEventsList(
                        characterId
                    )
                })


        val comicsPair = heroDetailsPageUIModel[0].first()
        val storiesPair = heroDetailsPageUIModel[1].first()
        val seriesPair = heroDetailsPageUIModel[2].first()
        val eventsPair = heroDetailsPageUIModel[3].first()

        emit(
            HeroDetailsPageUIModel(
                convertDataToTargetData(comicsPair),
                convertDataToTargetData(storiesPair),
                convertDataToTargetData(seriesPair),
                convertDataToTargetData(eventsPair)
            )
        )
    }.flowOn(iODispatcher).onStart { emit(HeroDetailsPageUIModel(APIState.LoadingState)) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataComics(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterComicsList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataStories(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterStoriesList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataSeries(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterSeriesList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataEvents(characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterEventsList(characterId).collect {
            emit(convertDataToTargetData(it))
        }
    }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    private fun convertDataToTargetData(state: APIState<MarvelCharacters>) =
        when (state) {
            is APIState.LoadingState -> state
            is APIState.ErrorState -> state
            is APIState.DataStat -> APIState.DataStat(state.value.data.results)
        }
}
