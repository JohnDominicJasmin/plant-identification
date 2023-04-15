package com.example.plantidentification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class WikiImage(
    @SerializedName("citation")
    val citation: String,
    @SerializedName("license_name")
    val licenseName: String,
    @SerializedName("license_url")
    val licenseUrl: String,
    @SerializedName("value")
    val value: String
)