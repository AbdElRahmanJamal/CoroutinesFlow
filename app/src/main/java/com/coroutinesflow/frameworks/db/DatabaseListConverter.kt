package com.coroutinesflow.frameworks.db

import androidx.room.TypeConverter
import com.coroutinesflow.base.data.entities.Items
import com.coroutinesflow.base.data.entities.Results
import com.coroutinesflow.base.data.entities.Urls
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class ResultsDataConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Results> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Results>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Results>): String {
        return gson.toJson(someObjects)
    }
}


class ItemsDataConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Items> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Items>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Items>): String {
        return gson.toJson(someObjects)
    }
}

class URlsDataConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Urls> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Urls>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Urls>): String {
        return gson.toJson(someObjects)
    }
}