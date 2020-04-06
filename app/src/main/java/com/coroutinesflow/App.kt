package com.coroutinesflow

import android.app.Application
import com.coroutinesflow.frameworks.network.apiFactory
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, listOf(apiFactory))
    }
}