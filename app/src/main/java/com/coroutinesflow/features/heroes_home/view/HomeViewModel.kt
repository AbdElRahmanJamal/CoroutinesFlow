package com.coroutinesflow.features.heroes_home.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.heroes_home.data.MarvelHomeRepository
import com.coroutinesflow.features.heroes_home.data.entities.Results
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class HomeViewModel(
    private val homeRepository: MarvelHomeRepository
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val listOfMarvelHeroesCharacters = MutableLiveData<APIState<List<Results>>>()

    @ExperimentalCoroutinesApi
    fun getListOfMarvelHeroesCharacters(
        limit: Int = 10,
        offset: Int = 0
    ): MutableLiveData<APIState<List<Results>>> {

        viewModelScope.launch {
            CoroutineScope(mainDispatcher).launch {
                homeRepository.getListOfMarvelHeroesCharacters(limit, offset).collect {
                    listOfMarvelHeroesCharacters.value = it
                }
            }
        }

        return listOfMarvelHeroesCharacters
    }

}
