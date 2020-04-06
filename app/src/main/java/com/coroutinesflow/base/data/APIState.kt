package com.coroutinesflow.base.data

sealed class APIState<out T : Any> {
    class DataStat<T : Any>(val value: T) : APIState<T>()
    class ErrorState(val exception: Throwable) : APIState<Nothing>()
    object LoadingState : APIState<Nothing>()
}