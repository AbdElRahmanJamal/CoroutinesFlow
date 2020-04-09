package com.coroutinesflow.features.hero_details.view

import androidx.lifecycle.MutableLiveData
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.view.BaseViewModel
import com.coroutinesflow.features.hero_details.data.domain.HeroDetailsUseCase
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsScreenSections
import com.coroutinesflow.features.heroes_home.data.entities.Results
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

const val CHARACTER_ID = "id"

class HeroDetailsViewModel(private val heroDetailsUseCase: HeroDetailsUseCase) : BaseViewModel() {

    private val heroDetailsPageDataComicsSeriesStoriesEvents =
        MutableLiveData<Pair<HeroDetailsScreenSections, APIState<List<Results>>>>()

    @ExperimentalCoroutinesApi
    override suspend fun callCustomAPI(data: HashMap<Any, Any>) {

        val characterId = data.getValue(CHARACTER_ID) as Int

        heroDetailsUseCase.getHeroDetailsPageDataComicsSeriesStoriesEvents(characterId)
            .collect { pairOfData ->
                pairOfData.second.collect { apiState ->
                    when (apiState) {
                        is APIState.LoadingState ->
                            heroDetailsPageDataComicsSeriesStoriesEvents.value =
                                pairOfData.first to apiState

                        is APIState.ErrorState ->
                            heroDetailsPageDataComicsSeriesStoriesEvents.value =
                                pairOfData.first to apiState

                        is APIState.DataStat ->
                            heroDetailsPageDataComicsSeriesStoriesEvents.value =
                                pairOfData.first to APIState.DataStat(apiState.value.data.results)
                    }
                }
            }
    }

    @ExperimentalCoroutinesApi
    fun getHeroDetailsPageDataComicsSeriesStoriesEvents(id: Int): MutableLiveData<Pair<HeroDetailsScreenSections, APIState<List<Results>>>> {

        val data = HashMap<Any, Any>()
        data[CHARACTER_ID] = id

        loadData(data)

        return heroDetailsPageDataComicsSeriesStoriesEvents
    }

}
