package com.coroutinesflow

import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHomeTable
import com.coroutinesflow.features.heroes_home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.heroes_home.data.local_datastore.db.MarvelCharactersDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestMarvelHomeLocalDataStore {

    @Mock
    private lateinit var marvelCharactersDao: MarvelCharactersDao
    private lateinit var marvelHomeLocalDataStore: MarvelHomeLocalDataStore
    val homeID = "homeID"

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        marvelHomeLocalDataStore = MarvelHomeLocalDataStore(marvelCharactersDao)
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

        val listOfMarvelCharacter = MarvelHomeTable(
            homeID,
            mutableListOf()
        )
        marvelHomeLocalDataStore.updateInsertMarvelHeroesCharacters(listOfMarvelCharacter)

        Mockito.verify(marvelCharactersDao).updateInsertMarvelHeroesCharacters(listOfMarvelCharacter)
    }
}