package com.coroutinesflow.frameworks.network.interceptor


import com.coroutinesflow.BuildConfig.*
import okhttp3.Interceptor
import okhttp3.Response


const val API_KEY_STRING = "apikey"
const val TIME_STAMP_STRING = "ts"
const val HASH_STRING = "hash"

class MarvelApiKeysInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter(API_KEY_STRING, API_KEY)
            .addQueryParameter(TIME_STAMP_STRING, TIME_STAMP)
            .addQueryParameter(HASH_STRING, HASH)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}