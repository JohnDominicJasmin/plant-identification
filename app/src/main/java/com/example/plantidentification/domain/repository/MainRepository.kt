package com.example.plantidentification.domain.repository

import com.example.plantidentification.data.dto.PlantApiResponse
import com.example.plantidentification.data.dto.PlantSpeciesDto
import com.example.plantidentification.data.dto.Suggestion

interface MainRepository {

    suspend fun recognizeFood(imageUri: String): Suggestion
}