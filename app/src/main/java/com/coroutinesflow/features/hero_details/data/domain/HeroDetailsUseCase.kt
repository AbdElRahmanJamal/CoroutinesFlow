package com.coroutinesflow.features.hero_details.data.domain

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsPageUIModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class HeroDetailsUseCase(
    private val heroDetailsRepository: HeroDetailsRepository,
    private val iODispatcher: CoroutineDispatcher
) {


    //i may use this function to call all apis in parallel
    //this not used for now
    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataComicsStoriesSeriesEvents(
        sectionID: String,
        characterId: Int
    ) = flow {
        val heroDetailsPageUIModel =
            awaitAll(
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterComicsList(
                        sectionID,
                        characterId
                    )
                },
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterStoriesList(
                        sectionID,
                        characterId
                    )
                },
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterSeriesList(
                        sectionID,
                        characterId
                    )
                },
                CoroutineScope(iODispatcher).async {
                    heroDetailsRepository.marvelHeroCharacterEventsList(
                        sectionID,
                        characterId
                    )
                })


        val comicsList = heroDetailsPageUIModel[0].first()
        val storiesList = heroDetailsPageUIModel[1].first()
        val seriesList = heroDetailsPageUIModel[2].first()
        val eventsList = heroDetailsPageUIModel[3].first()

        emit(
            HeroDetailsPageUIModel(
                comicsList,
                storiesList,
                seriesList,
                eventsList
            )
        )
    }.flowOn(iODispatcher).onStart { emit(HeroDetailsPageUIModel(APIState.LoadingState)) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataComics(sectionID: String, characterId: Int) =
        flow {
            heroDetailsRepository.marvelHeroCharacterComicsList(sectionID, characterId)
                .collect {
                    emit(it)
                }
        }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataStories(sectionID: String, characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterStoriesList(sectionID, characterId).collect {
            emit(it)
        }
    }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataSeries(sectionID: String, characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterSeriesList(sectionID, characterId).collect {
            emit(it)
        }
    }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataEvents(sectionID: String, characterId: Int) = flow {
        heroDetailsRepository.marvelHeroCharacterEventsList(sectionID, characterId).collect {
            emit(it)
        }
    }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }

}
