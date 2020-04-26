package com.coroutinesflow.hero_details_feature_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.coroutinesflow.AppExceptions
import com.coroutinesflow.MainRule
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsSectionsID
import com.coroutinesflow.features.hero_details.view.HeroDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestHeroDetailsViewModel {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainRule = MainRule(this)

    @Mock
    private lateinit var heroDetailsRepository: HeroDetailsRepository
    private lateinit var heroDetailsViewModel: HeroDetailsViewModel
    @Mock
    private lateinit var apiStateObserver: Observer<Any>
    private val apiID = "apiID"
    private val charID = 10

    private val resultList = mutableListOf<Results>()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        heroDetailsViewModel =
            HeroDetailsViewModel(heroDetailsRepository, mainRule.testCoroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get list of marvel heroes characters data state`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {

            val dataState = APIState.DataStat(resultList)
            val loadingState = APIState.LoadingState
            val sectionID = HeroDetailsSectionsID.COMICS

            Mockito.`when`(
                heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                    apiID,
                    sectionID,
                    charID
                )
            ).thenReturn(flow {
                emit(loadingState)
                emit(dataState)
            })

            heroDetailsViewModel.getHeroSectionListDetails(apiID, sectionID, charID)
                .observeForever(apiStateObserver)


            Mockito.verify(apiStateObserver, Mockito.times(1)).onChanged(APIState.LoadingState)
            Mockito.verify(apiStateObserver).onChanged(dataState)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get list of marvel heroes characters data state series`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {

            val dataState = APIState.DataStat(resultList)
            val loadingState = APIState.LoadingState
            val sectionID = HeroDetailsSectionsID.SERIES

            Mockito.`when`(
                heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                    apiID,
                    sectionID,
                    charID
                )
            ).thenReturn(flow {
                emit(loadingState)
                emit(dataState)
            })

            heroDetailsViewModel.getHeroSectionListDetails(apiID, sectionID, charID)
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
            val sectionID = HeroDetailsSectionsID.COMICS

            Mockito.`when`(
                heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                    apiID,
                    sectionID,
                    charID
                )
            ).thenReturn(flow {
                emit(loadingState)
                emit(errorState)
            })

            heroDetailsViewModel.getHeroSectionListDetails(apiID, sectionID, charID)
                .observeForever(apiStateObserver)

            Mockito.verify(apiStateObserver, Mockito.times(1)).onChanged(APIState.LoadingState)
            Mockito.verify(apiStateObserver).onChanged(errorState)

        }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get list of marvel heroes characters error state series`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {

            val errorState = APIState.ErrorState(AppExceptions.NullResponseException)
            val loadingState = APIState.LoadingState
            val sectionID = HeroDetailsSectionsID.SERIES

            Mockito.`when`(
                heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                    apiID,
                    sectionID,
                    charID
                )
            ).thenReturn(flow {
                emit(loadingState)
                emit(errorState)
            })

            heroDetailsViewModel.getHeroSectionListDetails(apiID, sectionID, charID)
                .observeForever(apiStateObserver)

            Mockito.verify(apiStateObserver, Mockito.times(1)).onChanged(APIState.LoadingState)
            Mockito.verify(apiStateObserver).onChanged(errorState)

        }

    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel aPI call success`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {
            Mockito.`when`(
                heroDetailsRepository.cancelAPICall(apiID)
            ).thenReturn(true)

            heroDetailsViewModel.cancelAPICall(apiID)
                .observeForever(apiStateObserver)

            Mockito.verify(apiStateObserver).onChanged(true)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel aPI call failed`() =
        mainRule.testCoroutineDispatcher.runBlockingTest {
            Mockito.`when`(
                heroDetailsRepository.cancelAPICall(apiID)
            ).thenReturn(false)

            heroDetailsViewModel.cancelAPICall(apiID)
                .observeForever(apiStateObserver)

            Mockito.verify(apiStateObserver).onChanged(false)
        }
}