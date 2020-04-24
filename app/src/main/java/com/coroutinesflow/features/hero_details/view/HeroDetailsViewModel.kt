package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class HeroDetailsViewModel(
    private val heroDetailsRepository: HeroDetailsRepository
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataComics(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsRepository.marvelHeroCharacterComicsList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataStories(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsRepository.marvelHeroCharacterStoriesList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataSeries(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsRepository.marvelHeroCharacterSeriesList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataEvents(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsRepository.marvelHeroCharacterEventsList(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    //here to cancel job "API call"
    fun cancelAPICall(apiID: String) = liveData {
        emit(heroDetailsRepository.cancelAPICall(apiID))
    }
}
