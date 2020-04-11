package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coroutinesflow.features.hero_details.data.domain.HeroDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher

class HeroDetailsViewModelFactory(
    private val heroDetailsUseCase: HeroDetailsUseCase
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeroDetailsViewModel(heroDetailsUseCase, mainDispatcher) as T
    }
}