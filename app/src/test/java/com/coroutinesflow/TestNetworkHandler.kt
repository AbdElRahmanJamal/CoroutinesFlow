package com.coroutinesflow

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Data
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.frameworks.network.NetworkHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Response
import java.io.IOException

@RunWith(PowerMockRunner::class)
@PrepareForTest(NetworkHandler::class)
class TestNetworkHandler {

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var networkHandler: NetworkHandler<MarvelCharacters>

    private val marvelCharacters = MarvelCharacters(
        0, 0, "", ""
        , "", "", "", Data(0, 0, 0, 0, mutableListOf())
    )
    private val apiID = "apiID"
    val TIMEOUTTIME = 11000L

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkHandler = spy(NetworkHandler(testCoroutineDispatcher))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_fail_NullResponseException_error_state() =
        testCoroutineDispatcher.runBlockingTest {

            val apiState = networkHandler.callAPI(apiID) {
                fakeApiRequestNullResponse()
            }.toList().first()

            Assert.assertTrue(apiState is APIState.ErrorState)
            Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.NullResponseException)

        }

    private fun fakeApiRequestNullResponse() = Response.success(null) as Response<MarvelCharacters>

    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_fail_EmptyResponseException_error_state() =
        testCoroutineDispatcher.runBlockingTest {

            val apiState = networkHandler.callAPI(apiID) {
                fakeApiRequestEmptyResponse()
            }.toList().first()

            Assert.assertTrue(apiState is APIState.ErrorState)
            Assert.assertTrue((apiState as APIState.ErrorState).throwable is AppExceptions.EmptyResponseException)

        }

    private fun fakeApiRequestEmptyResponse() = Response.success("") as Response<MarvelCharacters>


    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_success_data_state() = testCoroutineDispatcher.runBlockingTest {

        val apiState = networkHandler.callAPI(apiID) {
            fakeApiRequestSuccessState()
        }.toList().first()

        Assert.assertTrue(apiState is APIState.DataStat)

    }

    private fun fakeApiRequestSuccessState() = Response.success(marvelCharacters)


    @ExperimentalCoroutinesApi
    @Test
    fun test_callApi_fail_catch_io_exception() =
        testCoroutineDispatcher.runBlockingTest {

            val apiState = networkHandler.callAPI(apiID) {
                fakeApiRequestCatchIOException()
            }.toList().first()

            Assert.assertTrue(apiState is APIState.ErrorState)
            Assert.assertTrue((apiState as APIState.ErrorState).throwable is IOException)

        }

    private fun fakeApiRequestCatchIOException(): Response<MarvelCharacters> = throw IOException()


    @ExperimentalCoroutinesApi
    @Test
    fun test_callAdi_fail_catch_cancellation_exception() =
        testCoroutineDispatcher.runBlockingTest {

            val apiState = networkHandler.callAPI(apiID) {
                delay(TIMEOUTTIME)
                fakeApiRequestCatchCancellationException()
            }.toList().first()

            Assert.assertTrue(apiState is APIState.ErrorState)
            Assert.assertTrue((apiState as APIState.ErrorState).throwable is CancellationException)

        }

    private fun fakeApiRequestCatchCancellationException(): Response<MarvelCharacters> = Response.success(marvelCharacters)

}