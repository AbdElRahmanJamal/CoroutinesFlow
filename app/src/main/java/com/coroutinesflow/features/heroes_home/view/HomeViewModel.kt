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
        limit: Int = 10,
        offset: Int = 0
    ) = liveData(mainDispatcher) {
        homeRepository.getListOfMarvelHeroesCharacters(limit, offset).collect {
            emit(it)
        }
    }
}
