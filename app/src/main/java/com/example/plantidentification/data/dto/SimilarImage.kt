package com.example.plantidentification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SimilarImage(
    @SerializedName("citation")
    val citation: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("license_name")
    val licenseName: String,
    @SerializedName("license_url")
    val licenseUrl: String,
    @SerializedName("similarity")
    val similarity: Double,
    @SerializedName("url")
    val url: String,
    @SerializedName("url_small")
    val urlSmall: String
)