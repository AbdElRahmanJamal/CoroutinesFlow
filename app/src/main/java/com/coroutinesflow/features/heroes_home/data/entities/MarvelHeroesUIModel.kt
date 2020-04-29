package com.coroutinesflow.features.heroes_home.data.entities

import com.coroutinesflow.base.data.entities.Results

data class MarvelHeroesUIModel(
    val heroItem: Results,
    var onMarvelHeroClicked: ((item: Results) -> Unit)? = null
)