package com.coroutinesflow.marvel_home_feature_test

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Data
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHomeTable
import com.coroutinesflow.features.heroes_home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.heroes_home.data.local_datastore.db.MarvelCharactersDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

fun <T> anyOrNull(): T = Mockito.any<T>()


@RunWith(PowerMockRunner::class)
@PrepareForTest(MarvelHomeLocalDataStore::class)
class TestMarvelHomeLocalDataStore {

    @Mock
    private lateinit var marvelCharactersDao: MarvelCharactersDao
    private lateinit var marvelHomeLocalDataStore: MarvelHomeLocalDataStore
    private val homeID = "HomeID"

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        marvelHomeLocalDataStore = PowerMockito.spy(MarvelHomeLocalDataStore(marvelCharactersDao))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_data_from_data_base_data_exist() = runBlockingTest {

        val listOfHeroes = mutableListOf<Results>()
        listOfHeroes.add(
            Results(
                0, "", "", "",
                null, "", null, null, null, null, null, null
            )
        )
        Mockito.`when`(marvelCharactersDao.getListOfMarvelHeroesCharacters(10, 0, homeID))
            .thenReturn(
                MarvelHomeTable(homeID, listOfHeroes)
            )

        val homeData = marvelHomeLocalDataStore.getListOfMarvelHeroesCharacters(10, 0, homeID)

        Assert.assertTrue(homeData.screenID == homeID)
        Assert.assertFalse(homeData.listOfHeroes.isNullOrEmpty())
        Mockito.verify(marvelCharactersDao).getListOfMarvelHeroesCharacters(10, 0, homeID)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_data_from_data_base_no_data() = runBlockingTest {

        Mockito.`when`(marvelCharactersDao.getListOfMarvelHeroesCharacters(10, 0, homeID))
            .thenReturn(
                MarvelHomeTable("", mutableListOf())
            )

        val homeData = marvelHomeLocalDataStore.getListOfMarvelHeroesCharacters(10, 0, homeID)

        Assert.assertTrue(homeData.screenID.isEmpty())
        Assert.assertTrue(homeData.listOfHeroes.isNullOrEmpty())
        Mockito.verify(marvelCharactersDao).getListOfMarvelHeroesCharacters(10, 0, homeID)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_insert_data_into_data_base() = runBlockingTest {

        val marvelHomeTable = MarvelHomeTable(
            homeID,
            mutableListOf()
        )

        val marvelCharacters = MarvelCharacters(
            0, 0, "", ""
            , "", "", "", Data(0, 0, 0, 0, emptyList())
        )
        val dataState = APIState.DataStat(marvelCharacters)

        marvelHomeLocalDataStore.insertRemoteDataIntoDB(homeID, dataState)

        PowerMockito.doReturn(marvelHomeTable).`when`(
            marvelHomeLocalDataStore, "createMarvelHomeTableObject",
            homeID, dataState
        )

        PowerMockito.verifyPrivate(marvelHomeLocalDataStore).invoke(
            "createMarvelHomeTableObject",
            homeID, dataState
        )

        Mockito.verify(marvelCharactersDao).updateInsertMarvelHeroesCharacters(anyOrNull())

    }
}