package com.coroutinesflow.features.heroes_home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.coroutinesflow.features.heroes_home.data.MarvelHomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class HomeViewModel(
    private val homeRepository: MarvelHomeRepository
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun getListOfMarvelHeroesCharacters(
        apiID: String,
        limit: Int,
        offset: Int,
        homeID: String
    ) = liveData(mainDispatcher) {
        homeRepository.getListOfMarvelHeroesCharacters(apiID, limit, offset, homeID).collect {
            emit(it)
        }
    }

    //here to cancel job "API call"
    fun cancelAPICall(apiID: String) = liveData {
        emit(homeRepository.cancelAPICall(apiID))
    }
}
