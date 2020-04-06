package com.coroutinesflow.base.data


interface BaseModel<T> {
    fun retrieveData(): T
}