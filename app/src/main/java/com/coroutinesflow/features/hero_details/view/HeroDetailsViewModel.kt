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
    fun getHeroDetailsPageDataComics(characterId: Int) = liveData(mainDispatcher) {
        heroDetailsUseCase.getHeroDetailsPageDataComics(characterId)
            .collect {
                emit(it)
            }
    }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataStories(characterId: Int) = liveData(mainDispatcher) {
        heroDetailsUseCase.getHeroDetailsPageDataStories(characterId)
            .collect {
                emit(it)
            }
    }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataSeries(characterId: Int) = liveData(mainDispatcher) {
        heroDetailsUseCase.getHeroDetailsPageDataSeries(characterId)
            .collect {
                emit(it)
            }
    }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataEvents(characterId: Int) = liveData(mainDispatcher) {
        heroDetailsUseCase.getHeroDetailsPageDataEvents(characterId)
            .collect {
                emit(it)
            }
    }

}
