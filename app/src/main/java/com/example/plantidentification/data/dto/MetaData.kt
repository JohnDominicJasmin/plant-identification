package com.example.plantidentification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MetaData(
    @SerializedName("date")
    val date: String,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("latitude")
    val latitude: Any,
    @SerializedName("longitude")
    val longitude: Any
)