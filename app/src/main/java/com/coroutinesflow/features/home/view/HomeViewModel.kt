package com.coroutinesflow.features.home.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.features.home.data.MarvelHomeRepository
import com.coroutinesflow.features.home.model.MarvelCharacters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class HomeViewModel(
    private val homeRepository: MarvelHomeRepository
    , private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val listOfMarvelHeroesCharacters = MutableLiveData<APIState<MarvelCharacters>>()
    @ExperimentalCoroutinesApi
    fun getDataFlowState(limit: Int = 10, offset: Int = 0) {
        viewModelScope.launch {
            CoroutineScope(mainDispatcher).launch {
                homeRepository.getListOfMarvelHeroesCharacters(limit, offset).collect {
                    listOfMarvelHeroesCharacters.postValue(it)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun getListOfMarvelHeroesCharacters(limit: Int = 10, offset: Int = 0): MutableLiveData<APIState<MarvelCharacters>> {
        getDataFlowState(limit, offset)
        return listOfMarvelHeroesCharacters
    }

}
