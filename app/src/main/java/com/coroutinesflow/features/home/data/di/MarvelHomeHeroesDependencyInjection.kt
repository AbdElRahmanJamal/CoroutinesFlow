package com.coroutinesflow.features.home.data.di

import com.coroutinesflow.features.home.data.MarvelHomeRepository
import com.coroutinesflow.features.home.data.local_datastore.db.MarvelCharactersDB
import com.coroutinesflow.features.home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.home.data.remote_datastore.MarvelHomeRemoteDataStore
import com.coroutinesflow.features.home.view.HomeViewModelFactory
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module


object MarvelHomeHeroesDependencyInjection {

    val homeViewModelFactoryObject: Module = module {
        factory { HomeViewModelFactory(get()) }
        factory { MarvelHomeRepository(get(), get(), get()) }
        factory { MarvelHomeRemoteDataStore(get(), get()) }
        factory { MarvelHomeLocalDataStore(MarvelCharactersDB(androidApplication()).marvelCharactersDao()) }
        single { Dispatchers.IO }
    }

}
