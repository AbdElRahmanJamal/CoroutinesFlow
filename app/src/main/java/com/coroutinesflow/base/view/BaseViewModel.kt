package com.coroutinesflow.base.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

abstract class BaseViewModel(
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun loadData(data: HashMap<Any, Any>) {
        viewModelScope.launch {
            CoroutineScope(mainDispatcher).launch {
                callCustomAPI(data)
            }
        }
    }

    abstract suspend fun callCustomAPI(data: HashMap<Any, Any>)

}