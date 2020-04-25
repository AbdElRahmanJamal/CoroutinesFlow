package com.coroutinesflow.marvel_home_feature_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.coroutinesflow.AppExceptions
import com.coroutinesflow.MainRule
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.features.heroes_home.data.MarvelHomeRepository
import com.coroutinesflow.features.heroes_home.view.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestHomeViewModel {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainRule = MainRule(this)

    @Mock
    private lateinit var homeRepository: MarvelHomeRepository
    private lateinit var homeViewModel: HomeViewModel
    @Mock
    private lateinit var apiStateObserver: Observer<Any>
    private val apiID = "apiID"
    private val limit = 10
    private val offset = 0
    private val homeID = "homeID"
    private val resultList = mutableListOf<Results>()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(homeRepository, mainRule.testCoroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get list of marvel heroes characters data state`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {
            val dataState = APIState.DataStat(resultList)
            val loadingState = APIState.LoadingState

            Mockito.`when`(
                homeRepository.getListOfMarvelHeroesCharacters(
                    apiID,
                    limit,
                    offset,
                    homeID
                )
            ).thenReturn(flow {
                emit(loadingState)
                emit(dataState)
            })

            homeViewModel.getListOfMarvelHeroesCharacters(apiID, limit, offset, homeID)
                .observeForever(apiStateObserver)


            Mockito.verify(apiStateObserver, Mockito.times(1)).onChanged(APIState.LoadingState)
            Mockito.verify(apiStateObserver).onChanged(dataState)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get list of marvel heroes characters error state`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {
            val errorState = APIState.ErrorState(AppExceptions.NullResponseException)
            val loadingState = APIState.LoadingState

            Mockito.`when`(
                homeRepository.getListOfMarvelHeroesCharacters(
                    apiID,
                    limit,
                    offset,
                    homeID
                )
            ).thenReturn(flow {
                emit(loadingState)
                emit(errorState)
            })

            homeViewModel.getListOfMarvelHeroesCharacters(apiID, limit, offset, homeID)
                .observeForever(apiStateObserver)


            Mockito.verify(apiStateObserver, Mockito.times(1)).onChanged(APIState.LoadingState)
            Mockito.verify(apiStateObserver).onChanged(errorState)

        }


    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel cancel aPI call success`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {
            Mockito.`when`(
                homeRepository.cancelAPICall(apiID)
            ).thenReturn(true)

            homeViewModel.cancelAPICall(apiID)
                .observeForever(apiStateObserver)

            Mockito.verify(apiStateObserver).onChanged(true)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel cancel aPI call failed`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {
            Mockito.`when`(
                homeRepository.cancelAPICall(apiID)
            ).thenReturn(false)

            homeViewModel.cancelAPICall(apiID)
                .observeForever(apiStateObserver)

            Mockito.verify(apiStateObserver).onChanged(false)
        }
}