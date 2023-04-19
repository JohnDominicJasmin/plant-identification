package com.example.plantidentification.feature_plant_identification.presentation.choosing_image

data class MainState(
    val plantName:String = "",
    val plantDescription:String = "",
    val plantImageUrl:String = "",
    val startingDestination: String = "guidelines"
)
