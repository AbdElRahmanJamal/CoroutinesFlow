package com.coroutinesflow.features.heroes_home.data.local_datastore

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHomeTable
import com.coroutinesflow.features.heroes_home.data.local_datastore.db.MarvelCharactersDao

class MarvelHomeLocalDataStore(private val marvelCharactersDao: MarvelCharactersDao) {

    suspend fun insertRemoteDataIntoDB(
        homeID: String,
        state: APIState.DataStat<MarvelCharacters>
    ) {
        val marvelHomeTable = createMarvelHomeTableObject(homeID, state)
        marvelCharactersDao.updateInsertMarvelHeroesCharacters(marvelHomeTable)

    }

    private fun createMarvelHomeTableObject(
        homeID: String,
        state: APIState.DataStat<MarvelCharacters>
    ) = MarvelHomeTable(homeID, state.value.data.results)

    suspend fun getListOfMarvelHeroesCharacters(
        limit: Int = 15,
        offset: Int = 0,
        homeID: String
    ): MarvelHomeTable =
        marvelCharactersDao.getListOfMarvelHeroesCharacters(
            limit = limit,
            offset = offset,
            homeID = homeID
        )
}