package com.coroutinesflow.features.hero_details.data.local_datastore

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.hero_details.data.entities.MarvelHeroDetailsTable

class HeroDetailsLocalDataStore(private val marvelHeroDerailsDao: MarvelHeroDerailsDao) {


    suspend fun marvelHeroCharacterDetails(
        sectionID: String,
        charID: Int
    ) = marvelHeroDerailsDao.marvelHeroCharacterDetails(sectionID, charID)


    private fun createMarvelHeroDetailsTableObject(
        sectionID: String,
        characterId: Int,
        dataState: APIState.DataStat<MarvelCharacters>
    ) = MarvelHeroDetailsTable(sectionID, characterId, dataState.value.data.results)

    suspend fun insertRemoteDataIntoDB(
        sectionID: String,
        characterId: Int,
        dataState: APIState.DataStat<MarvelCharacters>
    ) {
        val marvelHeroDetailsTable =
            createMarvelHeroDetailsTableObject(sectionID, characterId, dataState)

        marvelHeroDerailsDao.updateInsertMarvelDetails(marvelHeroDetailsTable)
    }
}