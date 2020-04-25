package com.coroutinesflow.marvel_home_feature_test

import com.coroutinesflow.AppExceptions
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.base.data.entities.Data
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.heroes_home.data.remote_datastore.MarvelHomeRemoteDataStore
import com.coroutinesflow.frameworks.network.NetworkHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class TestMarvelHomeRemoteDataStore {

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var spyNetworkHandler: NetworkHandler<MarvelCharacters>

    @Mock
    private lateinit var aPIs: APIs

    private lateinit var marvelHomeRemoteDataStore: MarvelHomeRemoteDataStore


    private val marvelCharacters = MarvelCharacters(
        0, 0, "", ""
        , "", "", "", Data(0, 0, 0, 0, mutableListOf())
    )
    private val apiID = "apiID"

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        spyNetworkHandler = Mockito.spy(NetworkHandler(testCoroutineDispatcher))
        marvelHomeRemoteDataStore = MarvelHomeRemoteDataStore(spyNetworkHandler, aPIs)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_data_state() = runBlockingTest {

        Mockito.`when`(aPIs.getMarvelCharactersSuspend(10, 0)).thenReturn(
            fakeApiRequestDataResponse()
        )

        Mockito.`when`(spyNetworkHandler.callAPI(apiID) {
            any()
        }).thenReturn(flow {
            emit(APIState.DataStat(marvelCharacters))
        })

        val apiState =
            marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(apiID, 10, 0).first()


        Assert.assertTrue(apiState is APIState.DataStat)
        Mockito.verify(aPIs).getMarvelCharactersSuspend(10, 0)
    }

    private fun fakeApiRequestDataResponse() = Response.success(marvelCharacters)

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_null_exception_error_state() = runBlockingTest {

        Mockito.`when`(aPIs.getMarvelCharactersSuspend(10, 0)).thenReturn(
            fakeApiRequestNullExceptionResponse()
        )

        Mockito.`when`(spyNetworkHandler.callAPI(apiID) {
            any()
        }).thenReturn(flow {
            emit(APIState.ErrorState(AppExceptions.NullResponseException))
        })

        val apiState =
            marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(apiID, 10, 0).first()


        Assert.assertTrue(apiState is APIState.ErrorState)
        Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.NullResponseException)
        Mockito.verify(aPIs).getMarvelCharactersSuspend(10, 0)
    }

    private fun fakeApiRequestNullExceptionResponse() =
        Response.success(null) as Response<MarvelCharacters>

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_empty_exception_error_state() = runBlockingTest {

        Mockito.`when`(aPIs.getMarvelCharactersSuspend(10, 0)).thenReturn(
            fakeApiRequestEmptyExceptionResponse()
        )

        Mockito.`when`(spyNetworkHandler.callAPI(apiID) {
            any()
        }).thenReturn(flow {
            emit(APIState.ErrorState(AppExceptions.EmptyResponseException))
        })

        val apiState =
            marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(apiID, 10, 0).first()


        Assert.assertTrue(apiState is APIState.ErrorState)
        Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.EmptyResponseException)
        Mockito.verify(aPIs).getMarvelCharactersSuspend(10, 0)
    }

    private fun fakeApiRequestEmptyExceptionResponse() =
        Response.success("") as Response<MarvelCharacters>

    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel cancel aPI call success`() = runBlockingTest {
        Mockito.`when`(
            spyNetworkHandler.cancelJob(apiID)
        ).thenReturn(true)

        Assert.assertTrue(marvelHomeRemoteDataStore.cancelAPICall(apiID))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel cancel aPI call failed`() = runBlockingTest {
        Mockito.`when`(
            spyNetworkHandler.cancelJob(apiID)
        ).thenReturn(false)

        Assert.assertFalse(marvelHomeRemoteDataStore.cancelAPICall(apiID))
    }
}