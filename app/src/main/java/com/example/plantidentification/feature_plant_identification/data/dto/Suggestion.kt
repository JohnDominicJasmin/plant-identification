package com.example.plantidentification.feature_plant_identification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Suggestion(
    @SerializedName("confirmed")
    val confirmed: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("plant_details")
    val plantDetails: PlantDetails,
    @SerializedName("plant_name")
    val plantName: String,
    @SerializedName("probability")
    val probability: Double,
    @SerializedName("similar_images")
    val similarImages: List<SimilarImage>
)