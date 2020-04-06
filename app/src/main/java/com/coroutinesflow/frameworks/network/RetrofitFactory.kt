package com.coroutinesflow.frameworks.network

import com.coroutinesflow.BuildConfig.BASE_URL
import com.coroutinesflow.base.data.APIs
import com.coroutinesflow.frameworks.network.interceptor.ConnectivityInterceptor
import com.coroutinesflow.frameworks.network.interceptor.MarvelApiKeysInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiFactory = module {

    val keyConnectivityInterceptor = "ConnectivityInterceptor"
    val keyHeadersInterceptor = "HeadersInterceptor"
    val keyRetrofitOkHttpClient = "RetrofitOkHttpClient"


    single<Interceptor>(keyConnectivityInterceptor) { ConnectivityInterceptor(androidApplication()) }
    single<Interceptor>(keyHeadersInterceptor) { MarvelApiKeysInterceptor() }

    single(keyRetrofitOkHttpClient) {
        OkHttpClient.Builder()
            .addInterceptor(get(keyConnectivityInterceptor))
            .addInterceptor(get(keyHeadersInterceptor))
            .build()
    }

    single { GsonBuilder().serializeNulls().create() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(keyRetrofitOkHttpClient))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    factory { get<Retrofit>().create(APIs::class.java) }
}