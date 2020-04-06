package com.coroutinesflow.features.home.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Items(

    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("name") val name: String?
) : Parcelable