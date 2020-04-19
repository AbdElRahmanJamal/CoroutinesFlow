package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.coroutinesflow.features.hero_details.data.domain.HeroDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class HeroDetailsViewModel(
    private val heroDetailsUseCase: HeroDetailsUseCase
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataComics(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataComics(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataStories(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataStories(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataSeries(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataSeries(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataEvents(apiID: String, sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataEvents(apiID, sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    //here to cancel job "API call"
    fun cancelAPICall(apiID: String) = liveData {
        emit(heroDetailsUseCase.cancelAPICall(apiID))
    }
}
