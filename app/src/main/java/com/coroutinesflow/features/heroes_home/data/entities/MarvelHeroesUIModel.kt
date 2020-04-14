package com.coroutinesflow.features.heroes_home.data.entities

import com.coroutinesflow.base.data.entities.Results

class MarvelHeroesUIModel(
    val results: MutableList<Results>,
    var onMarvelHeroClicked: ((item: Results) -> Unit)? = null
)