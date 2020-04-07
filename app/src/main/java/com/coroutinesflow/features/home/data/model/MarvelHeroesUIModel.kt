package com.coroutinesflow.features.home.data.model

class MarvelHeroesUIModel(
    val results: MutableList<Results>,
    var onMarvelHeroClicked: ((item: Results) -> Unit)? = null
)