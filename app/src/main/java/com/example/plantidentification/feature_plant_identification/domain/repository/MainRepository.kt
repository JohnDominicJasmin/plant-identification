package com.example.plantidentification.feature_plant_identification.domain.repository

import com.example.plantidentification.feature_plant_identification.data.dto.PlantSpeciesDto
import com.example.plantidentification.feature_plant_identification.data.dto.Suggestion

interface MainRepository {

    suspend fun recognizeFood(imageUri: String): PlantSpeciesDto
}