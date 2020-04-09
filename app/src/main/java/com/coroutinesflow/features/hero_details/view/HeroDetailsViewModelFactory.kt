package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coroutinesflow.features.hero_details.data.domain.HeroDetailsUseCase

class HeroDetailsViewModelFactory(
    private val heroDetailsUseCase: HeroDetailsUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeroDetailsViewModel(heroDetailsUseCase) as T
    }
}