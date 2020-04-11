package com.coroutinesflow.features.heroes_home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coroutinesflow.features.heroes_home.data.MarvelHomeRepository
import kotlinx.coroutines.CoroutineDispatcher

class HomeViewModelFactory(
    private val homePageRepository: MarvelHomeRepository
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homePageRepository, mainDispatcher) as T
    }
}