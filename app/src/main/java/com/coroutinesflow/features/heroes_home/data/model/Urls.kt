package com.coroutinesflow.features.heroes_home.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Urls(

    @SerializedName("type") val type: String?,
    @SerializedName("url") val url: String?
) : Parcelable