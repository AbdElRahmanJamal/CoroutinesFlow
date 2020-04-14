package com.coroutinesflow.base.data

import com.coroutinesflow.base.data.entities.MarvelCharacters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIs {

    //Home Page Apis
    @GET("v1/public/characters")
    suspend fun getMarvelCharactersSuspend(
        @Query("limit") limit: Int, @Query("offset") offset: Int
    ): Response<MarvelCharacters>

    //Hero Details Page Apis
    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun marvelHeroCharacterComicsListSuspend(@Path("characterId") characterId: Int): Response<MarvelCharacters>

    @GET("/v1/public/characters/{characterId}/series")
    suspend fun marvelHeroCharacterSeriesListSuspend(@Path("characterId") characterId: Int): Response<MarvelCharacters>

    @GET("/v1/public/characters/{characterId}/stories")
    suspend fun marvelHeroCharacterStoriesListSuspend(@Path("characterId") characterId: Int): Response<MarvelCharacters>

    @GET("/v1/public/characters/{characterId}/events")
    suspend fun marvelHeroCharacterEventsListSuspend(@Path("characterId") characterId: Int): Response<MarvelCharacters>
}