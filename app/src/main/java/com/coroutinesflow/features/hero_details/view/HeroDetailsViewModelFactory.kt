package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher

class HeroDetailsViewModelFactory(
    private val heroDetailsRepository: HeroDetailsRepository
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeroDetailsViewModel(heroDetailsRepository, mainDispatcher) as T
    }
}