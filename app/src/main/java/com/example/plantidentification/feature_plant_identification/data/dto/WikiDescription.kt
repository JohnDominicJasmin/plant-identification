package com.example.plantidentification.feature_plant_identification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class WikiDescription(
    @SerializedName("citation")
    val citation: String,
    @SerializedName("license_name")
    val licenseName: String,
    @SerializedName("license_url")
    val licenseUrl: String,
    @SerializedName("value")
    val value: String
)