package com.coroutinesflow.features.hero_details.data.entities

import androidx.room.Entity
import androidx.room.TypeConverters
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.frameworks.db.ResultsDataConverter


@Entity(
    tableName = "marvel_hero_details_table",
    primaryKeys = ["sectionID", "heroID"]
)
class MarvelHeroDetailsTable(

    val sectionID: String,
    val heroID: Int,
    @TypeConverters(ResultsDataConverter::class) val heroSectionList: List<Results>
)