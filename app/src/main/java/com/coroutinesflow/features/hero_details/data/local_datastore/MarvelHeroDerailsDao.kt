package com.coroutinesflow.features.hero_details.data.local_datastore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coroutinesflow.features.hero_details.data.entities.MarvelHeroDetailsTable

@Dao
interface MarvelHeroDerailsDao {

    //hero details Page Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsertMarvelDetails(comicsListMarvel: MarvelHeroDetailsTable)

    @Query("select * from marvel_hero_details_table where sectionID=:sectionID and heroID=:charID")
    suspend fun getMarvelHeroCharacterDetails(
        sectionID: String,
        charID: Int
    ): MarvelHeroDetailsTable

}