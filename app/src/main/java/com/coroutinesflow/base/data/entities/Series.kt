package com.coroutinesflow.base.data.entities

import android.os.Parcelable
import androidx.room.TypeConverters
import com.coroutinesflow.frameworks.db.ItemsDataConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Series(

    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String?,
    @TypeConverters(ItemsDataConverter::class) @SerializedName("items") val items: List<Items>?,
    @SerializedName("returned") val returned: Int
) : Parcelable