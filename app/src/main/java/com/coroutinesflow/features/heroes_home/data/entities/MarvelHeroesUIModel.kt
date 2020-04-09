package com.coroutinesflow.features.heroes_home.data.entities

class MarvelHeroesUIModel(
    val results: MutableList<Results>,
    var onMarvelHeroClicked: ((item: Results) -> Unit)? = null
)