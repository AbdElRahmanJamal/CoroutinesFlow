package com.coroutinesflow.hero_details_feature_test

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Data
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.features.hero_details.data.entities.MarvelHeroDetailsTable
import com.coroutinesflow.features.hero_details.data.local_datastore.HeroDetailsLocalDataStore
import com.coroutinesflow.features.hero_details.data.local_datastore.MarvelHeroDerailsDao
import com.coroutinesflow.marvel_home_feature_test.anyOrNull
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

@RunWith(PowerMockRunner::class)
@PrepareForTest(HeroDetailsLocalDataStore::class)
class TestHeroDetailsLocalDataStore {
    @Mock
    private lateinit var marvelHeroDerailsDao: MarvelHeroDerailsDao
    private lateinit var heroDetailsLocalDataStore: HeroDetailsLocalDataStore
    private val sectionID = "comicsID"
    private val characterID = 1

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        heroDetailsLocalDataStore =
            PowerMockito.spy(HeroDetailsLocalDataStore(marvelHeroDerailsDao))
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
        Mockito.`when`(marvelHeroDerailsDao.marvelHeroCharacterDetails(sectionID, characterID))
            .thenReturn(
                MarvelHeroDetailsTable(sectionID, characterID, listOfHeroes)
            )
        val charData = heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterID)
        Assert.assertTrue(charData.sectionID == sectionID)
        Assert.assertTrue(charData.heroID == characterID)
        Assert.assertFalse(charData.heroSectionList.isNullOrEmpty())
        Mockito.verify(marvelHeroDerailsDao).marvelHeroCharacterDetails(sectionID, characterID)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_data_from_data_base_no_data() = runBlockingTest {

        Mockito.`when`(marvelHeroDerailsDao.marvelHeroCharacterDetails(sectionID, characterID))
            .thenReturn(
                MarvelHeroDetailsTable(sectionID, characterID, emptyList())
            )

        val charData = heroDetailsLocalDataStore.marvelHeroCharacterDetails(sectionID, characterID)

        Assert.assertTrue(charData.heroSectionList.isNullOrEmpty())
        Mockito.verify(marvelHeroDerailsDao).marvelHeroCharacterDetails(sectionID, characterID)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_insert_data_into_data_base() = runBlockingTest {

        val marvelHeroDetailsTable = MarvelHeroDetailsTable(sectionID, characterID, emptyList())

        val marvelCharacters = MarvelCharacters(
            0, 0, "", ""
            , "", "", "", Data(0, 0, 0, 0, emptyList())
        )
        val dataState = APIState.DataStat(marvelCharacters)

        heroDetailsLocalDataStore.insertRemoteDataIntoDB(sectionID, characterID, dataState)

        PowerMockito.doReturn(marvelHeroDetailsTable).`when`(
            heroDetailsLocalDataStore, "createMarvelHeroDetailsTableObject",
            sectionID, characterID, dataState
        )

        PowerMockito.verifyPrivate(heroDetailsLocalDataStore).invoke(
            "createMarvelHeroDetailsTableObject",
            sectionID, characterID, dataState
        )

        Mockito.verify(marvelHeroDerailsDao).updateInsertMarvelDetails(anyOrNull())

    }
}