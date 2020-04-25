package com.coroutinesflow

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Data
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.features.heroes_home.data.MarvelHomeRepository
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHomeTable
import com.coroutinesflow.features.heroes_home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.heroes_home.data.remote_datastore.MarvelHomeRemoteDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations


class TestMarvelHomeRepository {

    private lateinit var marvelHomeRepository: MarvelHomeRepository
    @Mock
    private lateinit var marvelHomeRemoteDataStore: MarvelHomeRemoteDataStore
    @Mock
    private lateinit var marvelHomeLocalDataStore: MarvelHomeLocalDataStore
    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val limit = 10
    private val offset = 0
    private val homeID = "homeID"
    private val apiID = "apiID"


    private val marvelCharacters = MarvelCharacters(
        0, 0, "", ""
        , "", "", "", Data(0, 0, 0, 0, listOfHeroesNotEmptyList())
    )
    private val dataState = APIState.DataStat(marvelCharacters)


    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        marvelHomeRepository = MarvelHomeRepository(
            marvelHomeRemoteDataStore,
            marvelHomeLocalDataStore,
            testCoroutineDispatcher
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get data from db local data store`() = runBlockingTest {

        Mockito.`when`(
            marvelHomeLocalDataStore.getListOfMarvelHeroesCharacters(
                limit,
                offset,
                homeID
            )
        ).thenReturn(MarvelHomeTable(homeID, listOfHeroesNotEmptyList()))

        val apiStates =
            marvelHomeRepository.getListOfMarvelHeroesCharacters(apiID, limit, offset, homeID)
                .toList()

        Mockito.verify(marvelHomeLocalDataStore).getListOfMarvelHeroesCharacters(
            limit,
            offset,
            homeID
        )
        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.DataStat)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store data state`() = runBlockingTest {

        Mockito.`when`(
            marvelHomeLocalDataStore.getListOfMarvelHeroesCharacters(
                limit,
                offset,
                homeID
            )
        ).thenReturn(MarvelHomeTable(homeID, emptyList()))

        Mockito.`when`(
            marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(
                apiID,
                limit,
                offset
            )
        ).thenReturn(flow {
            emit(dataState)
        })

        val apiStates =
            marvelHomeRepository.getListOfMarvelHeroesCharacters(apiID, limit, offset, homeID)
                .toList()

        Mockito.verify(marvelHomeLocalDataStore).getListOfMarvelHeroesCharacters(
            limit,
            offset,
            homeID
        )


        Mockito.verify(marvelHomeLocalDataStore).insertRemoteDataIntoDB(
            homeID,
            dataState
        )

        Mockito.verify(marvelHomeRemoteDataStore).getListOfMarvelHeroesCharacters(
            apiID,
            limit,
            offset
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.DataStat)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store error state`() = runBlockingTest {

        Mockito.`when`(
            marvelHomeLocalDataStore.getListOfMarvelHeroesCharacters(
                limit,
                offset,
                homeID
            )
        ).thenReturn(MarvelHomeTable(homeID, emptyList()))

        Mockito.`when`(
            marvelHomeRemoteDataStore.getListOfMarvelHeroesCharacters(
                apiID,
                limit,
                offset
            )
        ).thenReturn(flow {
            emit(APIState.ErrorState(AppExceptions.NullResponseException))
        })

        val apiStates =
            marvelHomeRepository.getListOfMarvelHeroesCharacters(apiID, limit, offset, homeID)
                .toList()

        Mockito.verify(marvelHomeLocalDataStore).getListOfMarvelHeroesCharacters(
            limit,
            offset,
            homeID
        )


        Mockito.verify(marvelHomeLocalDataStore, never()).insertRemoteDataIntoDB(
            homeID,
            dataState
        )

        Mockito.verify(marvelHomeRemoteDataStore).getListOfMarvelHeroesCharacters(
            apiID,
            limit,
            offset
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.ErrorState)
    }
}

private fun listOfHeroesNotEmptyList(): MutableList<Results> {
    val listOfHeroes = mutableListOf<Results>()

    listOfHeroes.add(
        Results(
            0, "", "", "",
            null, "", null, null, null, null, null, null
        )
    )
    return listOfHeroes
}
