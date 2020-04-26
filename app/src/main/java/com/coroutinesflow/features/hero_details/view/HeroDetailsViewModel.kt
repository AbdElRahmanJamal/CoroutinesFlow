package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsSectionsID

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class HeroDetailsViewModel(
    private val heroDetailsRepository: HeroDetailsRepository
    , private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun getHeroSectionListDetails(
        apiID: String,
        sectionID: HeroDetailsSectionsID,
        characterId: Int
    ) =
        liveData(mainDispatcher) {
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterId
            )
                .collect {
                    emit(it)
                }
        }

    //here to cancel job "API call"
    fun cancelAPICall(apiID: String) = liveData {
        emit(heroDetailsRepository.cancelAPICall(apiID))
    }
}
