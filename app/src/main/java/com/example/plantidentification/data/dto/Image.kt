package com.example.plantidentification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Image(
    @SerializedName("file_name")
    val fileName: String,
    @SerializedName("url")
    val url: String
)