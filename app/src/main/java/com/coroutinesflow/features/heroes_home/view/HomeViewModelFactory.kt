package com.coroutinesflow.features.heroes_home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coroutinesflow.features.heroes_home.data.MarvelHomeRepository

class HomeViewModelFactory(
    private val homePageRepository: MarvelHomeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homePageRepository) as T
    }
}