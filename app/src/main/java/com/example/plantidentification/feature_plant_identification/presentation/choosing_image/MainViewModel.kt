package com.example.plantidentification.feature_plant_identification.presentation.choosing_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantidentification.feature_plant_identification.data.dto.PlantSpeciesDto
import com.example.plantidentification.feature_plant_identification.data.dto.Suggestion
import com.example.plantidentification.feature_plant_identification.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    init{
        getStartingDestination()
    }

    fun recognizeFood(imagePath: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            runCatching {
                showLoading()
                mainRepository.recognizeFood(imagePath)
            }.onSuccess { plantSpecies ->
                hideLoading()
                plantSpecies.getPlantWithHighestProbability()?.let { plant ->
                    val name = plant.plantDetails.commonNames.maxBy { it.length }
                    val plantProbability = String.format("%.2f", plant.probability)

                    _state.update {
                        it.copy(
                            plantName = name,
                            plantDescription = plant.plantDetails.wikiDescription.value,
                            plantImageUrl = plantSpecies.images[0].url,
                            plantProbability = plantProbability
                        )
                    }

                    _event.emit(value = MainEvent.GetPlantInfo)
                }

            }.onFailure {
                hideLoading()
                 _event.emit(value = MainEvent.ShowToastMessage(message = it.message.toString()))
            }
        }
    }

    private fun showLoading(){
        _state.update { it.copy(
            isLoading = true
        )}
    }

    private fun hideLoading(){
        _state.update { it.copy(
            isLoading = false
        )}
    }

    private fun getStartingDestination(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                mainRepository.userReadAppInformation()
            }.onSuccess {userRead ->
                val startingDestination = if(userRead) "choosing-image" else "guidelines"
                Timber.v("Starting Destination: $startingDestination")
                _state.update {
                    it.copy(startingDestination = startingDestination)
                }
            }.onFailure {
                Timber.e("Error: ${it.localizedMessage}")
            }

        }
    }

    fun navigateToPlantInfoCompleted(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                mainRepository.setUserReadAppInformation()
            }.onSuccess {
                Timber.v("User read app information")
            }.onFailure {
                Timber.e("Error: ${it.localizedMessage}")
            }
        }
    }


    private fun PlantSpeciesDto.getPlantWithHighestProbability(): Suggestion? {
        var maxProbability = -1.0
        var maxPlant: Suggestion? = null
        if(suggestions.isEmpty()) return null
        suggestions.forEach {
            val probability = it.probability
            if (probability > maxProbability) {
                maxProbability = probability
                maxPlant = it
            }
        }
        return maxPlant
    }


}