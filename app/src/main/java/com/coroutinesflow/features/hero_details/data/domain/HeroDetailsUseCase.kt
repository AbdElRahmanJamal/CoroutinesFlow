package com.coroutinesflow.features.hero_details.data.domain

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsScreenSections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart


class HeroDetailsUseCase(
    private val heroDetailsRepository: HeroDetailsRepository,
    private val iODispatcher: CoroutineDispatcher
) {

    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataComicsSeriesStoriesEvents(characterId: Int) = flow {
        //call all apis in parallel way
        val marvelHeroCharacterComicsList =
            CoroutineScope(iODispatcher).async {
                heroDetailsRepository.marvelHeroCharacterComicsList(characterId)
                    .onStart { emit(APIState.LoadingState) }
            }
        val marvelHeroCharacterSeriesList =
            CoroutineScope(iODispatcher).async {
                heroDetailsRepository.marvelHeroCharacterSeriesList(characterId)
                    .onStart { emit(APIState.LoadingState) }
            }
        val marvelHeroCharacterStoriesList =
            CoroutineScope(iODispatcher).async {
                heroDetailsRepository.marvelHeroCharacterStoriesList(characterId)
                    .onStart { emit(APIState.LoadingState) }
            }
        val marvelHeroCharacterEventsList =
            CoroutineScope(iODispatcher).async {
                heroDetailsRepository.marvelHeroCharacterEventsList(characterId)
                    .onStart { emit(APIState.LoadingState) }
            }

        emit(HeroDetailsScreenSections.COMICS to marvelHeroCharacterComicsList.await())
        emit(HeroDetailsScreenSections.SERIES to marvelHeroCharacterSeriesList.await())
        emit(HeroDetailsScreenSections.STORIES to marvelHeroCharacterStoriesList.await())
        emit(HeroDetailsScreenSections.EVENTS to marvelHeroCharacterEventsList.await())

    }.flowOn(iODispatcher)
}
