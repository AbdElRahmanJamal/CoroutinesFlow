package com.coroutinesflow.features.heroes_home.data.local_datastore.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coroutinesflow.features.heroes_home.data.entities.MarvelHomeTable


@Dao
interface MarvelCharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsertMarvelHeroesCharacters(listOfMarvelCharacter: MarvelHomeTable)

    @Query("select * from marvel_heroes_list_table where screenID=:homeID LIMIT :limit OFFSET :offset")
    suspend fun getListOfMarvelHeroesCharacters(
        limit: Int,
        offset: Int,
        homeID: String
    ): MarvelHomeTable
}