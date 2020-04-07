package com.coroutinesflow.features.home.view

import androidx.lifecycle.MutableLiveData
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.view.BaseViewModel
import com.coroutinesflow.features.home.data.MarvelHomeRepository
import com.coroutinesflow.features.home.data.model.Results
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

const val LIMIT = "limit"
const val OFFSET = "offset"

class HomeViewModel(
    private val homeRepository: MarvelHomeRepository
    , mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : BaseViewModel(mainDispatcher) {

    private val listOfMarvelHeroesCharacters = MutableLiveData<APIState<List<Results>>>()

    @ExperimentalCoroutinesApi
    fun getListOfMarvelHeroesCharacters(
        limit: Int = 10,
        offset: Int = 0
    ): MutableLiveData<APIState<List<Results>>> {
        val data = createApiParameter(limit, offset)

        loadData(data)

        return listOfMarvelHeroesCharacters
    }

    private fun createApiParameter(
        limit: Int,
        offset: Int
    ): HashMap<Any, Any> {
        val data = HashMap<Any, Any>()

        data[LIMIT] = limit
        data[OFFSET] = offset
        return data
    }

    @ExperimentalCoroutinesApi
    override suspend fun callCustomAPI(data: HashMap<Any, Any>) {
        val limit = data.getValue(LIMIT) as Int
        val offset = data.getValue(OFFSET) as Int

        homeRepository.getListOfMarvelHeroesCharacters(limit, offset).collect {
            listOfMarvelHeroesCharacters.postValue(it)
        }
    }
}
