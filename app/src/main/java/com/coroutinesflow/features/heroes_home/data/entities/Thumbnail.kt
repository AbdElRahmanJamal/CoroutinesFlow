package com.coroutinesflow.features.heroes_home.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(

    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
) : Parcelable