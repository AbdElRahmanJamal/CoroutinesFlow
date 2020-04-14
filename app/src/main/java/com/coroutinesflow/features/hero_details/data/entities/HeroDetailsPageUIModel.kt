package com.coroutinesflow.features.hero_details.data.entities

import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.entities.Results

class HeroDetailsPageUIModel(
    val loadingState: APIState.LoadingState?,
    val comics: APIState<List<Results>>?,
    val stories: APIState<List<Results>>?,
    val series: APIState<List<Results>>?,
    val events: APIState<List<Results>>?
) {
    constructor(loadingState: APIState.LoadingState) : this(loadingState, null, null, null, null)

    constructor(
        comics: APIState<List<Results>>,
        stories: APIState<List<Results>>,
        series: APIState<List<Results>>,
        events: APIState<List<Results>>
    ) : this(null, comics, stories, series, events)
}