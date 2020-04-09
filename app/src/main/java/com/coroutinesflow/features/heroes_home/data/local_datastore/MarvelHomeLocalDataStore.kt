package com.coroutinesflow.features.heroes_home.data.local_datastore

import com.coroutinesflow.features.heroes_home.data.local_datastore.db.MarvelCharactersDao
import com.coroutinesflow.features.heroes_home.data.entities.Results

class MarvelHomeLocalDataStore(private val marvelCharactersDao: MarvelCharactersDao) {

    suspend fun updateInsert(listOfMarvelCharacter: List<Results>) =
        marvelCharactersDao.updateInsert(listOfMarvelCharacter)

    suspend fun getListOfMarvelHeroesCharacters(limit: Int = 15, offset: Int = 0) =
        marvelCharactersDao.getListOfMarvelHeroesCharacters(limit = limit, offset = offset)
}