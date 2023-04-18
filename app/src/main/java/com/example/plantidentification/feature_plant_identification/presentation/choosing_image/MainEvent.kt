package com.example.plantidentification.feature_plant_identification.presentation.choosing_image

sealed class MainEvent {

    object GetPlantInfo : MainEvent()

    data class ShowToastMessage(val message: String) : MainEvent()

}
