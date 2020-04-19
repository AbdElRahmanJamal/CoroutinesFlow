package com.coroutinesflow.features.heroes_home.data.local_datastore

import com.coroutinesflow.features.heroes_home.data.entities.MarvelHomeTable
import com.coroutinesflow.features.heroes_home.data.local_datastore.db.MarvelCharactersDao

class MarvelHomeLocalDataStore(private val marvelCharactersDao: MarvelCharactersDao) {

    suspend fun updateInsertMarvelHeroesCharacters(listOfMarvelCharacter: MarvelHomeTable) =
        marvelCharactersDao.updateInsertMarvelHeroesCharacters(listOfMarvelCharacter)

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