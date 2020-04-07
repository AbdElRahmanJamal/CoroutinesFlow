package com.coroutinesflow.features.home.data.di

import com.coroutinesflow.features.home.data.MarvelHomeRepository
import com.coroutinesflow.features.home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.home.data.remote_datastore.MarvelHomeRemoteDataStore
import com.coroutinesflow.features.home.view.HomeViewModelFactory
import org.koin.dsl.module.Module
import org.koin.dsl.module.module


object MarvelHomeHeroesDependencyInjection {

    val homeViewModelFactoryObject: Module = module {
        factory { HomeViewModelFactory(get()) }
        factory { MarvelHomeRepository(get(), get()) }
        factory { MarvelHomeRemoteDataStore(get()) }
        factory { MarvelHomeLocalDataStore() }
    }

}
