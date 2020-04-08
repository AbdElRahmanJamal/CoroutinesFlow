package com.coroutinesflow.features.heroes_home.data.local_datastore.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coroutinesflow.features.heroes_home.data.model.Results
import kotlinx.coroutines.flow.Flow


@Dao
interface MarvelCharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(listOfMarvelCharacter: List<Results>)

    @Query("select * from marvel_character_list LIMIT :limit OFFSET :offset")
     fun getListOfMarvelHeroesCharacters(limit: Int, offset: Int): Flow<List<Results>>
}