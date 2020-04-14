package com.coroutinesflow.features.heroes_home.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.frameworks.db.ResultsDataConverter

@Entity(tableName = "marvel_heroes_list_table")
class MarvelHomeTable(
    @PrimaryKey
    val screenID: String,
    @TypeConverters(ResultsDataConverter::class) val listOfHeroes: List<Results>
)
