package com.coroutinesflow.features.hero_details.data.di

import com.coroutinesflow.base.data.entities.MarvelCharacters
import com.coroutinesflow.features.hero_details.data.HeroDetailsRepository
import com.coroutinesflow.features.hero_details.data.domain.HeroDetailsUseCase
import com.coroutinesflow.features.hero_details.data.local_datastore.HeroDetailsLocalDataStore
import com.coroutinesflow.features.hero_details.data.remote_datastore.HeroDetailsRemoteDataStore
import com.coroutinesflow.features.hero_details.view.HeroDetailsViewModelFactory
import com.coroutinesflow.frameworks.db.MarvelCharactersDB
import com.coroutinesflow.frameworks.network.NetworkHandler
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

object MarvelHeroDetailsDependencyInjection {

    val heroDetailsViewModelFactoryObject: Module = module {
        factory { HeroDetailsViewModelFactory(get(), Dispatchers.Main) }
        factory { HeroDetailsUseCase(get(), Dispatchers.Main) }
        factory { HeroDetailsRepository(get(), get()) }
        factory { NetworkHandler<MarvelCharacters>() }
        factory { HeroDetailsRemoteDataStore(get(), get(), Dispatchers.Main) }
        factory {
            HeroDetailsLocalDataStore(
                MarvelCharactersDB(
                    androidApplication()
                ).marvelHeroDerailsDao()
            )
        }
    }
}
