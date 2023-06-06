package com.techholding.android.roammate.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Place(
    val placesId: String,
    val name: String,
    val city: String,
    val state: String,
    val country: String,
    val latitude: String,
    val description: String,
    val longitude: String,
    var distance: Double?,
    @SerializedName("logo") val logo: String
) : Serializable
