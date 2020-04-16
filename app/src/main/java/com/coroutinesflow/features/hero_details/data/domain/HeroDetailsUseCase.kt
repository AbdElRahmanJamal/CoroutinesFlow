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
//    suspend fun getHeroDetailsPageDataComicsStoriesSeriesEvents(sectionID: String,characterId: Int) = flow {
//        val heroDetailsPageUIModel =
//            awaitAll(
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterComicsList(
//                        characterId
//                    )
//                },
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterStoriesList(
//                        characterId
//                    )
//                },
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterSeriesList(
//                        characterId
//                    )
//                },
//                CoroutineScope(iODispatcher).async {
//                    heroDetailsRepository.marvelHeroCharacterEventsList(
//                        characterId
//                    )
//                })
//
//
//        val comicsPair = heroDetailsPageUIModel[0].first()
//        val storiesPair = heroDetailsPageUIModel[1].first()
//        val seriesPair = heroDetailsPageUIModel[2].first()
//        val eventsPair = heroDetailsPageUIModel[3].first()
//
//        emit(
//            HeroDetailsPageUIModel(
//                convertDataToTargetData(comicsPair),
//                convertDataToTargetData(storiesPair),
//                convertDataToTargetData(seriesPair),
//                convertDataToTargetData(eventsPair)
//            )
//        )
//    }.flowOn(iODispatcher).onStart { emit(HeroDetailsPageUIModel(APIState.LoadingState)) }


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
