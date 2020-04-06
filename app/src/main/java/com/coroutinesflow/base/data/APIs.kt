package com.coroutinesflow.base.data

import com.coroutinesflow.features.home.model.MarvelCharacters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIs {

    @GET("v1/public/characters")
    suspend fun getMarvelCharactersSuspend(@Query("limit") limit: Int, @Query("offset") offset: Int
    ): Response<MarvelCharacters>
}