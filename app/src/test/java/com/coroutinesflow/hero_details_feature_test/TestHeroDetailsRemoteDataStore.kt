package com.coroutinesflow.hero_details_feature_test

import com.coroutinesflow.AppExceptions
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.base.data.entities.Data
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.hero_details.data.remote_datastore.HeroDetailsRemoteDataStore
import com.coroutinesflow.frameworks.network.NetworkHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class TestHeroDetailsRemoteDataStore {

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    @Mock
    private lateinit var spyNetworkHandler: NetworkHandler<MarvelCharacters>

    @Mock
    private lateinit var aPIs: APIs

    private lateinit var heroDetailsRemoteDataStore: HeroDetailsRemoteDataStore

    private val marvelCharacters = MarvelCharacters(
        0, 0, "", ""
        , "", "", "", Data(0, 0, 0, 0, mutableListOf())
    )
    private val apiID = "apiID"
    private val characterID = 1

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        spyNetworkHandler = Mockito.spy(NetworkHandler(testCoroutineDispatcher))
        heroDetailsRemoteDataStore = HeroDetailsRemoteDataStore(spyNetworkHandler, aPIs)
    }

    private fun fakeApiRequestDataResponse() = Response.success(marvelCharacters)

    private fun fakeApiRequestNullExceptionResponse() =
        Response.success(null) as Response<MarvelCharacters>

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_data_state_comics_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterComicsListSuspend(characterID)).thenReturn(
            fakeApiRequestDataResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(apiID, characterID).first()


        Assert.assertTrue(apiState is APIState.DataStat)
        Mockito.verify(aPIs).marvelHeroCharacterComicsListSuspend(characterID)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_error_state_comics_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterComicsListSuspend(characterID)).thenReturn(
            fakeApiRequestNullExceptionResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(apiID, characterID).first()

        Assert.assertTrue(apiState is APIState.ErrorState)
        Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.NullResponseException)
        Mockito.verify(aPIs).marvelHeroCharacterComicsListSuspend(characterID)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_data_state_stories_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterStoriesListSuspend(characterID)).thenReturn(
            fakeApiRequestDataResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(apiID, characterID).first()


        Assert.assertTrue(apiState is APIState.DataStat)
        Mockito.verify(aPIs).marvelHeroCharacterStoriesListSuspend(characterID)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_error_state_stories_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterStoriesListSuspend(characterID)).thenReturn(
            fakeApiRequestNullExceptionResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(apiID, characterID).first()

        Assert.assertTrue(apiState is APIState.ErrorState)
        Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.NullResponseException)
        Mockito.verify(aPIs).marvelHeroCharacterStoriesListSuspend(characterID)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_data_state_series_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterSeriesListSuspend(characterID)).thenReturn(
            fakeApiRequestDataResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(apiID, characterID).first()


        Assert.assertTrue(apiState is APIState.DataStat)
        Mockito.verify(aPIs).marvelHeroCharacterSeriesListSuspend(characterID)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_error_state_series_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterSeriesListSuspend(characterID)).thenReturn(
            fakeApiRequestNullExceptionResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(apiID, characterID).first()

        Assert.assertTrue(apiState is APIState.ErrorState)
        Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.NullResponseException)
        Mockito.verify(aPIs).marvelHeroCharacterSeriesListSuspend(characterID)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_data_state_events_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterEventsListSuspend(characterID)).thenReturn(
            fakeApiRequestDataResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(apiID, characterID).first()


        Assert.assertTrue(apiState is APIState.DataStat)
        Mockito.verify(aPIs).marvelHeroCharacterEventsListSuspend(characterID)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_error_state_events_list() = runBlockingTest {

        Mockito.`when`(aPIs.marvelHeroCharacterEventsListSuspend(characterID)).thenReturn(
            fakeApiRequestNullExceptionResponse()
        )

        val apiState =
            heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(apiID, characterID).first()

        Assert.assertTrue(apiState is APIState.ErrorState)
        Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.NullResponseException)
        Mockito.verify(aPIs).marvelHeroCharacterEventsListSuspend(characterID)
    }

}