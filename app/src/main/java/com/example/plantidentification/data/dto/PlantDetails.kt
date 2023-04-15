package com.example.plantidentification.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PlantDetails(
    @SerializedName("common_names")
    val commonNames: List<String>,
    @SerializedName("edible_parts")
    val edibleParts: List<String>,
    @SerializedName("propagation_methods")
    val propagationMethods: List<String>,
    @SerializedName("scientific_name")
    val scientificName: String,
    @SerializedName("structured_name")
    val structuredName: StructuredName,
    @SerializedName("synonyms")
    val synonyms: List<String>,
    @SerializedName("taxonomy")
    val taxonomy: Taxonomy,
    @SerializedName("url")
    val url: String,
    @SerializedName("wiki_description")
    val wikiDescription: WikiDescription,
    @SerializedName("wiki_image")
    val wikiImage: WikiImage
)