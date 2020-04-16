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
    fun getHeroDetailsPageDataComics(sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataComics(sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataStories(sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataStories(sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataSeries(sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataSeries(sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataEvents(sectionID: String, characterId: Int) =
        liveData(mainDispatcher) {
            heroDetailsUseCase.getHeroDetailsPageDataEvents(sectionID, characterId)
                .collect {
                    emit(it)
                }
        }

}
