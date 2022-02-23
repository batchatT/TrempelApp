package com.example.domain_layer.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class
LoginResponse(

    @SerializedName("token")
    val token: String

) : Parcelable
