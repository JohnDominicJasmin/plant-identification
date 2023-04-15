package com.example.plantidentification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StructuredName(
    @SerializedName("genus")
    val genus: String,
    @SerializedName("species")
    val species: String
)