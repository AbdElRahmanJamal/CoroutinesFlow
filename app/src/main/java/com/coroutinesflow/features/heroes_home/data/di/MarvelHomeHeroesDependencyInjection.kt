package com.coroutinesflow.features.heroes_home.data.di

import com.coroutinesflow.features.heroes_home.data.MarvelHomeRepository
import com.coroutinesflow.features.heroes_home.data.local_datastore.MarvelHomeLocalDataStore
import com.coroutinesflow.features.heroes_home.data.remote_datastore.MarvelHomeRemoteDataStore
import com.coroutinesflow.features.heroes_home.view.HomeViewModelFactory
import com.coroutinesflow.frameworks.db.MarvelCharactersDB
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module


object MarvelHomeHeroesDependencyInjection {

    val homeViewModelFactoryObject: Module = module {
        factory { HomeViewModelFactory(get(), Dispatchers.Main) }
        factory { MarvelHomeRepository(get(), get(), Dispatchers.IO) }
        factory { MarvelHomeRemoteDataStore(get(), Dispatchers.IO) }
        factory {
            MarvelHomeLocalDataStore(
                MarvelCharactersDB(
                    androidApplication()
                ).marvelCharactersDao()
            )
        }
    }

}
