package com.coroutinesflow.hero_details_feature_test

import com.coroutinesflow.AppExceptions
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Data
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.hero_details.data.entities.HeroDetailsSectionsID
import com.coroutinesflow.features.hero_details.data.entities.MarvelHeroDetailsTable
import com.coroutinesflow.features.hero_details.data.local_datastore.HeroDetailsLocalDataStore
import com.coroutinesflow.features.hero_details.data.remote_datastore.HeroDetailsRemoteDataStore
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

class TestHeroDetailsRepository {

    @Mock
    private lateinit var heroDetailsRemoteDataStore: HeroDetailsRemoteDataStore
    @Mock
    private lateinit var heroDetailsLocalDataStore: HeroDetailsLocalDataStore
    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var heroDetailsRepository: HeroDetailsRepository

    private val marvelCharacters = MarvelCharacters(
        0, 0, "", ""
        , "", "", "", Data(0, 0, 0, 0, mutableListOf())
    )

    private val apiID = "apiID"
    private val characterID = 1

    private val dataState = APIState.DataStat(marvelCharacters)

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        heroDetailsRepository = HeroDetailsRepository(
            heroDetailsRemoteDataStore,
            heroDetailsLocalDataStore,
            testCoroutineDispatcher
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get data from db local data store comics`() = runBlockingTest {

        val sectionIDName = HeroDetailsSectionsID.COMICS.name
        val sectionID = HeroDetailsSectionsID.COMICS

        Mockito.`when`(
            heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
        ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, listOfHeroesNotEmptyList()))

        val apiStates =
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterID
            )
                .toList()

        Mockito.verify(heroDetailsLocalDataStore)
            .getMarvelHeroCharacterDetails(sectionIDName, characterID)

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.DataStat)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store data state comics`() {

        runBlockingTest {

            val sectionIDName = HeroDetailsSectionsID.COMICS.name
            val sectionID = HeroDetailsSectionsID.COMICS

            Mockito.`when`(
                heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
            ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

            Mockito.`when`(
                heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(
                    apiID,
                    characterID
                )
            ).thenReturn(flow {
                emit(dataState)
            })

            val apiStates =
                heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                    apiID,
                    sectionID,
                    characterID
                )
                    .toList()

            Mockito.verify(heroDetailsLocalDataStore)
                .getMarvelHeroCharacterDetails(sectionIDName, characterID)


            Mockito.verify(heroDetailsLocalDataStore).insertRemoteDataIntoDB(
                sectionIDName,
                characterID,
                dataState
            )

            Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterComicsList(
                apiID,
                characterID
            )

            Assert.assertTrue(apiStates.size == 2)
            Assert.assertTrue(apiStates[0] is APIState.LoadingState)
            Assert.assertTrue(apiStates[1] is APIState.DataStat)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store error state comics`() {

        runBlockingTest {

            val sectionIDName = HeroDetailsSectionsID.COMICS.name
            val sectionID = HeroDetailsSectionsID.COMICS

            Mockito.`when`(
                heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
            ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

            Mockito.`when`(
                heroDetailsRemoteDataStore.marvelHeroCharacterComicsList(
                    apiID,
                    characterID
                )
            ).thenReturn(flow {
                emit(APIState.ErrorState(AppExceptions.NullResponseException))
            })

            val apiStates =
                heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                    apiID,
                    sectionID,
                    characterID
                )
                    .toList()

            Mockito.verify(heroDetailsLocalDataStore)
                .getMarvelHeroCharacterDetails(sectionIDName, characterID)


            Mockito.verify(heroDetailsLocalDataStore, never()).insertRemoteDataIntoDB(
                sectionIDName,
                characterID,
                dataState
            )

            Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterComicsList(
                apiID,
                characterID
            )

            Assert.assertTrue(apiStates.size == 2)
            Assert.assertTrue(apiStates[0] is APIState.LoadingState)
            Assert.assertTrue(apiStates[1] is APIState.ErrorState)
            Assert.assertTrue((apiStates[1] as APIState.ErrorState).throwable is AppExceptions.NullResponseException)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store data state stories`() = runBlockingTest {

        val sectionIDName = HeroDetailsSectionsID.STORIES.name
        val sectionID = HeroDetailsSectionsID.STORIES

        Mockito.`when`(
            heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
        ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

        Mockito.`when`(
            heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(
                apiID,
                characterID
            )
        ).thenReturn(flow {
            emit(dataState)
        })

        val apiStates =
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterID
            )
                .toList()

        Mockito.verify(heroDetailsLocalDataStore)
            .getMarvelHeroCharacterDetails(sectionIDName, characterID)


        Mockito.verify(heroDetailsLocalDataStore).insertRemoteDataIntoDB(
            sectionIDName,
            characterID,
            dataState
        )

        Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterStoriesList(
            apiID,
            characterID
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.DataStat)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store error state stories`() = runBlockingTest {

        val sectionIDName = HeroDetailsSectionsID.STORIES.name
        val sectionID = HeroDetailsSectionsID.STORIES

        Mockito.`when`(
            heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
        ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

        Mockito.`when`(
            heroDetailsRemoteDataStore.marvelHeroCharacterStoriesList(
                apiID,
                characterID
            )
        ).thenReturn(flow {
            emit(APIState.ErrorState(AppExceptions.EmptyResponseException))
        })

        val apiStates =
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterID
            )
                .toList()

        Mockito.verify(heroDetailsLocalDataStore)
            .getMarvelHeroCharacterDetails(sectionIDName, characterID)


        Mockito.verify(heroDetailsLocalDataStore, never()).insertRemoteDataIntoDB(
            sectionIDName,
            characterID,
            dataState
        )

        Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterStoriesList(
            apiID,
            characterID
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.ErrorState)
        Assert.assertTrue((apiStates[1] as APIState.ErrorState).throwable is AppExceptions.EmptyResponseException)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store data state series`() = runBlockingTest {

        val sectionIDName = HeroDetailsSectionsID.SERIES.name
        val sectionID = HeroDetailsSectionsID.SERIES

        Mockito.`when`(
            heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
        ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

        Mockito.`when`(
            heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(
                apiID,
                characterID
            )
        ).thenReturn(flow {
            emit(dataState)
        })

        val apiStates =
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterID
            )
                .toList()

        Mockito.verify(heroDetailsLocalDataStore)
            .getMarvelHeroCharacterDetails(sectionIDName, characterID)


        Mockito.verify(heroDetailsLocalDataStore).insertRemoteDataIntoDB(
            sectionIDName,
            characterID,
            dataState
        )

        Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterSeriesList(
            apiID,
            characterID
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.DataStat)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store error state series`() = runBlockingTest {

        val sectionIDName = HeroDetailsSectionsID.SERIES.name
        val sectionID = HeroDetailsSectionsID.SERIES

        Mockito.`when`(
            heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
        ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

        Mockito.`when`(
            heroDetailsRemoteDataStore.marvelHeroCharacterSeriesList(
                apiID,
                characterID
            )
        ).thenReturn(flow {
            emit(APIState.ErrorState(AppExceptions.GenericErrorException))
        })

        val apiStates =
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterID
            )
                .toList()

        Mockito.verify(heroDetailsLocalDataStore)
            .getMarvelHeroCharacterDetails(sectionIDName, characterID)


        Mockito.verify(heroDetailsLocalDataStore,never()).insertRemoteDataIntoDB(
            sectionIDName,
            characterID,
            dataState
        )

        Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterSeriesList(
            apiID,
            characterID
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.ErrorState)
        Assert.assertTrue((apiStates[1] as APIState.ErrorState).throwable is AppExceptions.GenericErrorException)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store data state events`() = runBlockingTest {

        val sectionIDName = HeroDetailsSectionsID.EVENTS.name
        val sectionID = HeroDetailsSectionsID.EVENTS

        Mockito.`when`(
            heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
        ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

        Mockito.`when`(
            heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(
                apiID,
                characterID
            )
        ).thenReturn(flow {
            emit(dataState)
        })

        val apiStates =
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterID
            )
                .toList()

        Mockito.verify(heroDetailsLocalDataStore)
            .getMarvelHeroCharacterDetails(sectionIDName, characterID)


        Mockito.verify(heroDetailsLocalDataStore).insertRemoteDataIntoDB(
            sectionIDName,
            characterID,
            dataState
        )

        Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterEventsList(
            apiID,
            characterID
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.DataStat)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test get remote data remote data store error state events`() = runBlockingTest {

        val sectionIDName = HeroDetailsSectionsID.EVENTS.name
        val sectionID = HeroDetailsSectionsID.EVENTS

        Mockito.`when`(
            heroDetailsLocalDataStore.getMarvelHeroCharacterDetails(sectionIDName, characterID)
        ).thenReturn(MarvelHeroDetailsTable(sectionIDName, characterID, emptyList()))

        Mockito.`when`(
            heroDetailsRemoteDataStore.marvelHeroCharacterEventsList(
                apiID,
                characterID
            )
        ).thenReturn(flow {
            emit(APIState.ErrorState(AppExceptions.NoConnectivityException))
        })

        val apiStates =
            heroDetailsRepository.getMarvelHeroCharacterDetailsSectionList(
                apiID,
                sectionID,
                characterID
            )
                .toList()

        Mockito.verify(heroDetailsLocalDataStore)
            .getMarvelHeroCharacterDetails(sectionIDName, characterID)


        Mockito.verify(heroDetailsLocalDataStore, never()).insertRemoteDataIntoDB(
            sectionIDName,
            characterID,
            dataState
        )

        Mockito.verify(heroDetailsRemoteDataStore).marvelHeroCharacterEventsList(
            apiID,
            characterID
        )

        Assert.assertTrue(apiStates.size == 2)
        Assert.assertTrue(apiStates[0] is APIState.LoadingState)
        Assert.assertTrue(apiStates[1] is APIState.ErrorState)
        Assert.assertTrue((apiStates[1] as APIState.ErrorState).throwable is AppExceptions.NoConnectivityException)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel aPI call success`() = runBlockingTest {
        Mockito.`when`(
            heroDetailsRemoteDataStore.cancelAPICall(apiID)
        ).thenReturn(true)

        Assert.assertTrue(heroDetailsRepository.cancelAPICall(apiID))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test cancel aPI call failed`() = runBlockingTest {
        Mockito.`when`(
            heroDetailsRemoteDataStore.cancelAPICall(apiID)
        ).thenReturn(false)

        Assert.assertFalse(heroDetailsRepository.cancelAPICall(apiID))
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

}