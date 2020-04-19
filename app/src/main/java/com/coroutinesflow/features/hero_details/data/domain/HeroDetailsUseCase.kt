package com.coroutinesflow.features.hero_details.data.domain

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart


class HeroDetailsUseCase(
    private val heroDetailsRepository: HeroDetailsRepository,
    private val iODispatcher: CoroutineDispatcher
) {


    //i may use this function to call all apis in parallel
    //this not used for now
//    @ExperimentalCoroutinesApi
//    suspend fun getHeroDetailsPageDataComicsStoriesSeriesEvents(
//
//        sectionID: String,
//        characterId: Int
//    ) = flow {
//        val heroDetailsPageUIModel =
//            awaitAll(
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterComicsList(
//                        sectionID,
//                        characterId
//                    )
//                },
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterStoriesList(
//                        sectionID,
//                        characterId
//                    )
//                },
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterSeriesList(
//                        sectionID,
//                        characterId
//                    )
//                },
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterEventsList(
//                        sectionID,
//                        characterId
//                    )
//                })
//
//
//        val comicsList = heroDetailsPageUIModel[0].first()
//        val storiesList = heroDetailsPageUIModel[1].first()
//        val seriesList = heroDetailsPageUIModel[2].first()
//        val eventsList = heroDetailsPageUIModel[3].first()
//
//        emit(
//            HeroDetailsPageUIModel(
//                comicsList,
//                storiesList,
//                seriesList,
//                eventsList
//            )
//        )
//    }.flowOn(iODispatcher).onStart { emit(HeroDetailsPageUIModel(APIState.LoadingState)) }
//

    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataComics(apiID: String, sectionID: String, characterId: Int) =
        flow {
            heroDetailsRepository.marvelHeroCharacterComicsList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataStories(apiID: String, sectionID: String, characterId: Int) =
        flow {
            heroDetailsRepository.marvelHeroCharacterStoriesList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataSeries(apiID: String, sectionID: String, characterId: Int) =
        flow {
            heroDetailsRepository.marvelHeroCharacterSeriesList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }


    @ExperimentalCoroutinesApi
    suspend fun getHeroDetailsPageDataEvents(apiID: String, sectionID: String, characterId: Int) =
        flow {
            heroDetailsRepository.marvelHeroCharacterEventsList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }.flowOn(iODispatcher).onStart { emit(APIState.LoadingState) }

    fun cancelAPICall(apiID: String) = heroDetailsRepository.cancelAPICall(apiID)
}
