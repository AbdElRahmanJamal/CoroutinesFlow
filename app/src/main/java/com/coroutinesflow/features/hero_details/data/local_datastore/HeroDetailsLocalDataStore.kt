package com.coroutinesflow.features.hero_details.data.local_datastore

import com.coroutinesflow.features.hero_details.data.entities.MarvelHeroDetailsTable

class HeroDetailsLocalDataStore(private val marvelHeroDerailsDao: MarvelHeroDerailsDao) {

    suspend fun updateInsertMarvelDetails(comicsListMarvel: MarvelHeroDetailsTable) =
        marvelHeroDerailsDao.updateInsertMarvelDetails(comicsListMarvel)

    suspend fun marvelHeroCharacterDetails(
        sectionID: String,
        charID: Int
    ) = marvelHeroDerailsDao.marvelHeroCharacterDetails(sectionID, charID)
}