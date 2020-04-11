package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.hero_details.data.domain.HeroDetailsUseCase
import com.coroutinesflow.features.heroes_home.data.entities.Results
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HeroDetailsViewModel(
    private val heroDetailsUseCase: HeroDetailsUseCase
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val heroDetailsPageDataComics = MutableLiveData<APIState<List<Results>>>()
    private val heroDetailsPageDataSeries = MutableLiveData<APIState<List<Results>>>()
    private val heroDetailsPageDataStories = MutableLiveData<APIState<List<Results>>>()
    private val heroDetailsPageDataEvents = MutableLiveData<APIState<List<Results>>>()

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataComics(characterId: Int): MutableLiveData<APIState<List<Results>>> {

        viewModelScope.launch {
            CoroutineScope(mainDispatcher).launch {
                heroDetailsUseCase.getHeroDetailsPageDataComics(characterId)
                    .collect {
                        heroDetailsPageDataComics.value = it
                    }
            }
        }

        return heroDetailsPageDataComics
    }


    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataStories(characterId: Int): MutableLiveData<APIState<List<Results>>> {

        viewModelScope.launch {
            CoroutineScope(mainDispatcher).launch {
                heroDetailsUseCase.getHeroDetailsPageDataStories(characterId)
                    .collect {
                        heroDetailsPageDataStories.value = it
                    }
            }
        }

        return heroDetailsPageDataStories
    }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataSeries(characterId: Int): MutableLiveData<APIState<List<Results>>> {

        viewModelScope.launch {
            CoroutineScope(mainDispatcher).launch {
                heroDetailsUseCase.getHeroDetailsPageDataSeries(characterId)
                    .collect {
                        heroDetailsPageDataSeries.value = it
                    }
            }
        }
        return heroDetailsPageDataSeries
    }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataEvents(characterId: Int): MutableLiveData<APIState<List<Results>>> {

        viewModelScope.launch {
            CoroutineScope(mainDispatcher).launch {
                heroDetailsUseCase.getHeroDetailsPageDataEvents(characterId)
                    .collect {
                        heroDetailsPageDataEvents.value = it
                    }
            }
        }
        return heroDetailsPageDataEvents
    }

}
